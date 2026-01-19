INSERT INTO aeronaves (

    nome,
    fabricante,
    ano_fabricacao,
    descricao,
    vendido,
    created_at,
    update_at
) VALUES
      ( 'Phenom 100', 'EMBRAER', 2011,
       'Jato executivo leve com excelente desempenho e baixo custo operacional.',
       false, CURRENT_TIMESTAMP - INTERVAL '15' DAY, CURRENT_TIMESTAMP - INTERVAL '10' DAY),

      ('Phenom 300', 'EMBRAER', 2018,
       'Jato executivo de médio porte, líder mundial em vendas na categoria.',
       true, CURRENT_TIMESTAMP - INTERVAL '20' DAY, CURRENT_TIMESTAMP - INTERVAL '5' DAY),

      ('Legacy 450', 'EMBRAER', 2016,
       'Jato executivo com alcance intercontinental e cabine silenciosa.',
       false, CURRENT_TIMESTAMP - INTERVAL '7' DAY, CURRENT_TIMESTAMP - INTERVAL '3' DAY),

      ('E195-E2', 'EMBRAER', 2020,
       'Aeronave comercial moderna com menor consumo de combustível.',
       false, CURRENT_TIMESTAMP - INTERVAL '4' DAY, CURRENT_TIMESTAMP - INTERVAL '1' DAY),

      ('Boeing 737-800', 'BOEING', 2010,
       'Aeronave narrow-body amplamente utilizada em voos comerciais.',
       true, CURRENT_TIMESTAMP - INTERVAL '30' DAY, CURRENT_TIMESTAMP - INTERVAL '20' DAY),

      ('Boeing 737 MAX 8', 'BOEING', 2021,
       'Modelo atualizado com maior eficiência e tecnologia avançada.',
       false, CURRENT_TIMESTAMP - INTERVAL '6' DAY, CURRENT_TIMESTAMP - INTERVAL '2' DAY),

      ('Boeing 787 Dreamliner', 'BOEING', 2019,
       'Aeronave wide-body com fuselagem em materiais compostos.',
       true, CURRENT_TIMESTAMP - INTERVAL '25' DAY, CURRENT_TIMESTAMP - INTERVAL '12' DAY),

      ('Boeing 777-300ER', 'BOEING', 2015,
       'Aeronave de longo alcance para rotas internacionais.',
       false, CURRENT_TIMESTAMP - INTERVAL '9' DAY, CURRENT_TIMESTAMP - INTERVAL '4' DAY),

      ('Airbus A320', 'AIRBUS', 2012,
       'Modelo consagrado no transporte aéreo de curta e média distância.',
       true, CURRENT_TIMESTAMP - INTERVAL '40' DAY, CURRENT_TIMESTAMP - INTERVAL '25' DAY),

      ('Airbus A320neo', 'AIRBUS', 2022,
       'Versão otimizada com novos motores e menor emissão de ruído.',
       false, CURRENT_TIMESTAMP - INTERVAL '3' DAY, CURRENT_TIMESTAMP - INTERVAL '1' DAY),

      ('Airbus A321', 'AIRBUS', 2014,
       'Aeronave com maior capacidade de passageiros da família A320.',
       true, CURRENT_TIMESTAMP - INTERVAL '18' DAY, CURRENT_TIMESTAMP - INTERVAL '8' DAY),

      ('Airbus A350', 'AIRBUS', 2019,
       'Wide-body de nova geração com alta eficiência operacional.',
       false, CURRENT_TIMESTAMP - INTERVAL '11' DAY, CURRENT_TIMESTAMP - INTERVAL '5' DAY),

      ('KC-390 Millennium', 'EMBRAER', 2023,
       'Aeronave militar multimissão de transporte tático.',
       false, CURRENT_TIMESTAMP - INTERVAL '2' DAY, CURRENT_TIMESTAMP - INTERVAL '1' DAY),

      ('Boeing 747-400', 'BOEING', 1999,
       'Clássico jato de grande porte, ícone da aviação comercial.',
       true, CURRENT_TIMESTAMP - INTERVAL '90' DAY, CURRENT_TIMESTAMP - INTERVAL '60' DAY),

      ('Airbus A330', 'AIRBUS', 2006,
       'Aeronave wide-body para rotas regionais e internacionais.',
       false, CURRENT_TIMESTAMP - INTERVAL '14' DAY, CURRENT_TIMESTAMP - INTERVAL '7' DAY);