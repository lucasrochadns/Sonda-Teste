import { useEffect, useMemo, useState } from "react";
import { api } from "./api/api";
import type {
  AeronaveDTO,
  Fabricante,
  Page,
  AeronavePorDecadaDTO,
  AeronavePorFabricanteDTO,
} from "./api/types";
import "./styles.css";

function asArray<T>(v: unknown): T[] {
  return Array.isArray(v) ? (v as T[]) : [];
}

// Normaliza /por-decada para o layout da tela (Década 90, 00, 10...)
function normalizeDecadas(raw: unknown): { label: string; qtd: number; ano: number }[] {
  const arr = Array.isArray(raw) ? raw : [];
  return arr
    .map((x: any) => {
      const ano = Number(x?.anoFabricacao);
      const decade2 = String(ano).slice(2, 4);
      const lista = Array.isArray(x?.aeronaveDTO) ? x.aeronaveDTO : [];
      return { label: `Década ${decade2}`, qtd: lista.length, ano };
    })
    .filter((d) => Number.isFinite(d.ano))
    .sort((a, b) => a.ano - b.ano);
}

// Normaliza /por-fabricante para "Marcas" com contagem
function normalizeFabricantes(raw: unknown): { label: string; qtd: number }[] {
  const arr = Array.isArray(raw) ? raw : [];

  return arr
    .map((x: any) => {
      const label = String(x?.fabricante ?? "").trim();
      const lista = Array.isArray(x?.aeronaveDTO) ? x.aeronaveDTO : [];
      return { label, qtd: lista.length };
    })
    .filter((x) => x.label.length > 0)
    .sort((a, b) => a.label.localeCompare(b.label));
}

const fabricantes: { label: string; value: Fabricante }[] = [
  { label: "Embraer", value: "EMBRAER" },
  { label: "Boeing", value: "BOEING" },
  { label: "Airbus", value: "AIRBUS" },
];

export default function App() {
  // form
  const [fabricante, setFabricante] = useState<Fabricante>("EMBRAER");
  const [nome, setNome] = useState("");
  const [anoFabricacao, setAnoFabricacao] = useState<number>(2020);
  const [vendido, setVendido] = useState<boolean>(false);

  // list / page
  const [pageData, setPageData] = useState<Page<AeronaveDTO> | null>(null);
  const [page, setPage] = useState(0);
  const size = 12;

  // search
  const [termo, setTermo] = useState("");
  const [searching, setSearching] = useState(false);
  const [searchResults, setSearchResults] = useState<AeronaveDTO[] | null>(null);

  // stats
  const [decadasRaw, setDecadasRaw] = useState<unknown>([]);
  const [fabricantesRaw, setFabricantesRaw] = useState<unknown>([]);
  const [ultimaSemana, setUltimaSemana] = useState<AeronaveDTO[]>([]);

  // ui
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const decadas = useMemo(() => normalizeDecadas(decadasRaw), [decadasRaw]);
  const marcas = useMemo(() => normalizeFabricantes(fabricantesRaw), [fabricantesRaw]);

  const tableItems = useMemo(() => {
    if (searchResults) return searchResults;
    return pageData?.content ?? [];
  }, [searchResults, pageData]);

  async function loadAll() {
    setError(null);
    try {
      const [p, dec, fab, ult] = await Promise.all([
        api.list(page, size),
        api.porDecada(),
        api.porFabricante(),
        api.ultimaSemana(),
      ]);

      setPageData(p);
      setDecadasRaw(dec);
      setFabricantesRaw(fab);
      setUltimaSemana(Array.isArray(ult) ? ult : []);
    } catch (e: any) {
      setError(e?.message ?? "Erro ao carregar dados.");
      setPageData(null);
      setSearchResults(null);
    }
  }

  useEffect(() => {
    loadAll();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  async function onGravar() {
    setBusy(true);
    setError(null);
    try {
      const payload: Omit<AeronaveDTO, "id"> = {
        nome: nome.trim(),
        fabricante,
        anoFabricacao: Number(anoFabricacao),
        descricao: "Cadastro via UI",
        vendido,
        createdAt: new Date().toISOString(),
        updateAt: new Date().toISOString(),
      };

      if (!payload.nome) throw new Error("Modelo (nome) é obrigatório.");
      if (!payload.anoFabricacao || payload.anoFabricacao < 1900) throw new Error("Ano inválido.");

      await api.create(payload);

      setNome("");
      setAnoFabricacao(2020);
      setVendido(false);

      // volta para listagem normal
      setSearchResults(null);
      setTermo("");

      await loadAll();
    } catch (e: any) {
      setError(e?.message ?? "Erro ao gravar.");
    } finally {
      setBusy(false);
    }
  }

  async function onPesquisar() {
    const t = termo.trim();
    if (!t) {
      setSearchResults(null);
      return;
    }
    setSearching(true);
    setError(null);
    try {
      const res = await api.find(t);
      setSearchResults(Array.isArray(res) ? res : []);
    } catch (e: any) {
      setError(e?.message ?? "Erro na pesquisa.");
      setSearchResults([]);
    } finally {
      setSearching(false);
    }
  }

  async function onExcluir(id?: number) {
    if (typeof id !== "number") return;

    setBusy(true);
    setError(null);
    try {
      await api.remove(id);
      await loadAll();
    } catch (e: any) {
      setError(e?.message ?? "Erro ao excluir.");
    } finally {
      setBusy(false);
    }
  }

  async function toggleVendido(a: AeronaveDTO) {
    if (typeof a.id !== "number") return;
    setBusy(true);
    setError(null);
    try {
      await api.patch(a.id, { vendido: !a.vendido });
      await loadAll();
      // se está pesquisando, refaça a pesquisa pra refletir a mudança
      if (searchResults && termo.trim()) await onPesquisar();
    } catch (e: any) {
      setError(e?.message ?? "Erro ao atualizar vendido.");
    } finally {
      setBusy(false);
    }
  }

  return (
    <div className="page">
      <h1 className="title">Gestão de Aeronaves</h1>

      {error && <div className="error">{error}</div>}

      {/* FORM (igual imagem) */}
      <div className="formBox">
        <div className="row">
          <div className="field">
            <label>Marca</label>
            <select value={fabricante} onChange={(e) => setFabricante(e.target.value as Fabricante)} disabled={busy}>
              {fabricantes.map((f) => (
                <option key={f.value} value={f.value}>
                  {f.label}
                </option>
              ))}
            </select>
          </div>

          <div className="field">
            <label>Aeronave</label>
            <input value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Modelo" disabled={busy} />
          </div>
        </div>

        <div className="row">
          <div className="field small">
            <label>Ano</label>
            <input
              type="number"
              value={anoFabricacao}
              onChange={(e) => setAnoFabricacao(Number(e.target.value))}
              disabled={busy}
            />
          </div>

          <div className="field small">
            <label>Vendido</label>
            <select value={vendido ? "true" : "false"} onChange={(e) => setVendido(e.target.value === "true")} disabled={busy}>
              <option value="false">Não</option>
              <option value="true">Sim</option>
            </select>
          </div>
        </div>

        <button className="btn" onClick={onGravar} disabled={busy}>
          {busy ? "Aguarde..." : "Gravar"}
        </button>
      </div>

      {/* STATS (Décadas à esquerda / Essa semana ao centro) */}
      <div className="statsRow">
        <div className="decadas">
          {decadas.map((d) => (
            <div key={d.label} className="statLine">
              <b>{d.label}</b>: {d.qtd} Aeronaves
            </div>
          ))}
        </div>

        <div className="semana">
          <b>Essa semana:</b> {ultimaSemana.length} aeronaves
        </div>
      </div>

      {/* SEARCH */}
      <div className="searchRow">
        <input
          className="search"
          placeholder="Pesquisa por Modelo ou ID"
          value={termo}
          onChange={(e) => setTermo(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && onPesquisar()}
          disabled={busy}
        />
        <button className="btnSecondary" onClick={onPesquisar} disabled={busy || searching}>
          {searching ? "..." : "Pesquisar"}
        </button>
        <button
          className="btnSecondary"
          onClick={() => {
            setTermo("");
            setSearchResults(null);
          }}
          disabled={busy}
        >
          Limpar
        </button>
      </div>

      {/* TABLE */}
      <div className="tableBox">
        <table className="table">
          <thead>
            <tr>
              <th style={{ width: 60 }}>ID</th>
              <th style={{ width: 120 }}>Marca</th>
              <th>Modelo</th>
              <th style={{ width: 80 }}>Ano</th>
              <th style={{ width: 90 }}>Vendido</th>
              <th style={{ width: 80 }}>Excluir</th>
            </tr>
          </thead>
          <tbody>
            {tableItems.map((a, idx) => (
              <tr key={a.id ?? idx}>
                <td>{a.id ?? "—"}</td>
                <td>{labelMarca(a.fabricante)}</td>
                <td>{a.nome}</td>
                <td>{a.anoFabricacao}</td>
                <td>
                  <button className="vendidoDot" onClick={() => toggleVendido(a)} title="Alternar vendido" disabled={busy}>
                    {a.vendido ? "◉" : "○"}
                  </button>
                </td>
                <td>
                  <button className="deleteBtn" onClick={() => onExcluir(a.id)} disabled={busy}>
                    X
                  </button>
                </td>
              </tr>
            ))}

            {tableItems.length === 0 && (
              <tr>
                <td colSpan={6} className="empty">
                  Nenhum registro.
                </td>
              </tr>
            )}
          </tbody>
        </table>

        {/* Paginação (somente quando não está pesquisando) */}
        {!searchResults && pageData && (
          <div className="pagination">
            <button className="btnSecondary" disabled={pageData.first || busy} onClick={() => setPage((p) => Math.max(0, p - 1))}>
              ◀
            </button>
            <span>
              Página {pageData.number + 1} / {pageData.totalPages} — Total: {pageData.totalElements}
            </span>
            <button className="btnSecondary" disabled={pageData.last || busy} onClick={() => setPage((p) => p + 1)}>
              ▶
            </button>
          </div>
        )}
      </div>

      {/* Marcas (rodapé igual imagem) */}
      <div className="marcas">
        <b>Marcas</b>
        <div className="marcasList">
          {marcas.map((m) => (
            <div key={m.label}>
              {labelMarca(m.label)}: {m.qtd}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

function labelMarca(f: any) {
  const s = String(f ?? "").toUpperCase();
  if (s === "EMBRAER") return "Embraer";
  if (s === "BOEING") return "Boeing";
  if (s === "AIRBUS") return "Airbus";
  return s || "—";
}
