-- Tabela de configurações do sistema
CREATE TABLE configuracoes_sistema (
    chave VARCHAR(100) PRIMARY KEY,
    valor TEXT NOT NULL,
    nome VARCHAR(100),
    descricao TEXT,
    tipo_dado VARCHAR(20),
    valor_padrao TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(100)
);

-- Inserir configurações padrão

-- Configurações de Período de Teste
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('PERIODO_TESTE_DIAS', '15', 'Período de Teste (dias)', 'Número de dias do período de teste gratuito', 'INTEGER', '15', 'SYSTEM');

-- Configurações de Validação de Fotos
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('MIN_FOTOS_VEICULO', '3', 'Mínimo de Fotos do Veículo', 'Quantidade mínima de fotos do veículo necessárias', 'INTEGER', '3', 'SYSTEM'),
('MAX_FILE_SIZE_MB', '10', 'Tamanho Máximo de Arquivo (MB)', 'Tamanho máximo permitido para upload de arquivos em megabytes', 'INTEGER', '10', 'SYSTEM');

-- Configurações de Validação de Texto
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('MIN_DESCRICAO_LENGTH', '50', 'Tamanho Mínimo da Descrição', 'Quantidade mínima de caracteres para a descrição', 'INTEGER', '50', 'SYSTEM');

-- Mensagens Automáticas - WhatsApp
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('MENSAGEM_WHATSAPP_CONTRATANTE', 'Olá! Vi seu perfil na plataforma Veni Fretes e gostaria de solicitar um orçamento.', 'Mensagem WhatsApp - Contratante', 'Mensagem enviada quando um contratante clica no WhatsApp do freteiro', 'TEXT', 'Olá! Vi seu perfil na plataforma Veni Fretes e gostaria de solicitar um orçamento.', 'SYSTEM');

-- Mensagens de Boas-vindas
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('MENSAGEM_BOAS_VINDAS_FRETEIRO', 'Bem-vindo à Veni Fretes! Complete seu perfil para começar a receber propostas.', 'Mensagem Boas-vindas - Freteiro', 'Mensagem de boas-vindas enviada para novos freteiros', 'TEXT', 'Bem-vindo à Veni Fretes! Complete seu perfil para começar a receber propostas.', 'SYSTEM'),
('MENSAGEM_BOAS_VINDAS_CONTRATANTE', 'Bem-vindo à Veni Fretes! Encontre os melhores freteiros da sua região.', 'Mensagem Boas-vindas - Contratante', 'Mensagem de boas-vindas enviada para novos contratantes', 'TEXT', 'Bem-vindo à Veni Fretes! Encontre os melhores freteiros da sua região.', 'SYSTEM');

-- Mensagens de Expiração
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('MENSAGEM_EXPIRACAO_TESTE', 'Seu período de teste expira em {dias} dias. Assine um plano para continuar visível!', 'Mensagem Expiração - Período de Teste', 'Mensagem enviada quando o período de teste está prestes a expirar', 'TEXT', 'Seu período de teste expira em {dias} dias. Assine um plano para continuar visível!', 'SYSTEM'),
('MENSAGEM_ASSINATURA_EXPIRADA', 'Sua assinatura expirou. Renove agora para continuar recebendo propostas!', 'Mensagem Assinatura Expirada', 'Mensagem enviada quando a assinatura expira', 'TEXT', 'Sua assinatura expirou. Renove agora para continuar recebendo propostas!', 'SYSTEM');

-- Configurações de Notificações
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('HABILITAR_NOTIFICACOES_EMAIL', 'true', 'Habilitar Notificações por Email', 'Ativa ou desativa o envio de notificações por email', 'BOOLEAN', 'true', 'SYSTEM'),
('HABILITAR_NOTIFICACOES_WHATSAPP', 'false', 'Habilitar Notificações por WhatsApp', 'Ativa ou desativa o envio de notificações por WhatsApp', 'BOOLEAN', 'false', 'SYSTEM');

-- Configurações de Ranking
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('DIAS_INATIVIDADE_PENALIZACAO', '30', 'Dias de Inatividade para Penalização', 'Número de dias sem login para aplicar penalização no ranking', 'INTEGER', '30', 'SYSTEM');

-- Configurações de Pontos
INSERT INTO configuracoes_sistema (chave, valor, nome, descricao, tipo_dado, valor_padrao, updated_by) VALUES
('VALOR_PONTO_REAIS', '1.00', 'Valor do Ponto em Reais', 'Valor em reais de cada ponto para compra', 'DECIMAL', '1.00', 'SYSTEM'),
('PONTOS_POR_AVALIACAO', '10', 'Pontos por Avaliação Recebida', 'Quantidade de pontos ganhos ao receber uma avaliação', 'INTEGER', '10', 'SYSTEM');

-- Criar índice para melhor performance
CREATE INDEX idx_configuracoes_updated_at ON configuracoes_sistema(updated_at);
