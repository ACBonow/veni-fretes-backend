-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 29/12/2025 às 17:07
-- Versão do servidor: 11.8.3-MariaDB-log
-- Versão do PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `u108549867_ngrinbg`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `cadastro_fretes`
--

CREATE TABLE `cadastro_fretes` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `senha` varchar(100) DEFAULT NULL,
  `telefone` varchar(20) NOT NULL,
  `veiculo` text DEFAULT NULL,
  `dias` text DEFAULT NULL,
  `modelo` text DEFAULT NULL,
  `servicos` text DEFAULT NULL,
  `mudanca` varchar(10) DEFAULT NULL,
  `estado` varchar(100) DEFAULT NULL,
  `cidade` varchar(100) DEFAULT NULL,
  `pontos` int(11) DEFAULT 0,
  `token_redefinicao_senha` varchar(255) DEFAULT NULL,
  `expiracao_token` datetime DEFAULT NULL,
  `destaque_ate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Despejando dados para a tabela `cadastro_fretes`
--

INSERT INTO `cadastro_fretes` (`id`, `nome`, `email`, `foto_perfil`, `senha`, `telefone`, `veiculo`, `dias`, `modelo`, `servicos`, `mudanca`, `estado`, `cidade`, `pontos`, `token_redefinicao_senha`, `expiracao_token`, `destaque_ate`) VALUES
(4, 'Devonildo', '', NULL, '', '53984350612', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(5, 'Ubiraja Souza Gonçalves', '', NULL, '', '53984189505', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(6, 'Enilton', '', NULL, '', '53984534942', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(7, 'Ricardo', '', NULL, '', '53981128258', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(8, 'Emerson', '', NULL, '', '53981356886', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(9, 'William', '', NULL, '', '53999994019', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(10, 'Mudanças Gigio', '', NULL, '', '53999823734', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(11, 'Márcio', '', NULL, '', '53984646200', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(12, 'Valter', '', NULL, '', '53981215670', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(13, 'Elton', '', NULL, '', '53984165425', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(14, 'Guilherme', '', NULL, '', '53981337545', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(15, 'Alcides', '', NULL, '', '53984365744', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(16, 'Gabriel SS Cargas e Mudanças', '', NULL, '', '53984480596', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(17, 'Everton Pinto', '', NULL, '', '53981206925', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(18, 'Ari', '', NULL, '', '53984836976', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(19, 'Nathan Frete Papaléguas', '', NULL, '', '53984788784', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(20, 'Gilnei Delta Transportes', '', NULL, '', '53999827428', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(21, 'Ciro', '', NULL, '', '53991747286', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(22, 'Valnei', '', NULL, '', '53984365074', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, NULL, 0, NULL, NULL, NULL),
(23, 'Rubinho', '', NULL, '', '53991396893', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(24, 'Disk Frete', '', NULL, '', '53984033236', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(25, 'Márcio Goya', '', NULL, '', '53984035912', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(26, 'Tiago', '', NULL, '', '53984521858', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(27, 'Patrick Lima', '', NULL, '', '53984422375', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(28, 'Almeida Fretes', '', NULL, '', '53981131496', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(29, 'Wagner Radmann', '', NULL, '', '53991427206', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(30, 'Frete Souza', '', NULL, '', '53984635398', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(31, 'Marcos', '', NULL, '', '53991723034', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, NULL, 0, NULL, NULL, NULL),
(41, 'Alemão Fretes e Mudanças', '', NULL, '', '53991237803', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Porto Alegre, Canoas, Pelotas, Santa Maria, Caxias do Sul', 0, NULL, NULL, NULL),
(42, 'Marcelo Fretes', '', NULL, '', '53991565568', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(43, 'Aldo Fretes', '', NULL, '', '53984554098', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(44, 'Luan Leão', '', NULL, '', '53984283032', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(45, 'Luizinho Mudanças', '', NULL, '', '53984061239', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(46, 'Transleão Mudanças', '', NULL, '', '53999831994', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(47, 'Bastos Mudanças', '', NULL, '', '53981322167', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(48, 'Correa Mudanças', '', NULL, '', '53981165030', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(49, 'DMN Mudanças', '', NULL, '', '53984755327', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(50, 'Evertou Mudança', '', NULL, '', '53984332433', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(51, 'Expresso Paganine', '', NULL, '', '53981160240', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(52, 'Fretes Vilmar', '', NULL, '', '53999827264', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(53, 'JS Transportes', '', NULL, '', '53984142914', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(54, 'Pedronia Transportes', '', NULL, '', '53984227738', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(55, 'Ricardo Duro Fretes', '', NULL, '', '53991389014', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(56, 'The Flash Transportes', '', NULL, '', '53984012371', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(57, 'Valdeci Fretes', '', NULL, '', '53981214808', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(58, 'Fretes e Brick Carlos', '', NULL, '', '53981152955', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(59, 'Geziel Pacheco de Medeiros', '', NULL, '', '53984233190', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(60, 'Jader Log', '', NULL, '', '53984813617', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(63, 'Medina Fretes', '', NULL, '', '53984730069', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(64, 'Orlando Fretes', '', NULL, '', '53984345450', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(65, 'Pardal Fretes', '', NULL, '', '53999885119', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(66, 'Rangel Fretes', '', NULL, '', '53984249503', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(67, 'Frete Fátima', '', NULL, '', '53999825301', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(68, 'Paulinho Fretes', '', NULL, '', '53984271642', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(69, 'Rafael Douglas', '', NULL, '', '53981320834', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(70, 'Barriga Transportes', '', NULL, '', '53991254662', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', 'RS', '', 0, NULL, NULL, NULL),
(71, 'Fretes Birajara', '', NULL, '', '5332275760', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(72, 'Brick e Fretes Laranjal', '', NULL, '', '5332261064', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(73, 'Transportadora Gil', '', NULL, '', '5332732418', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(74, 'Einhard Transportadora', '', NULL, '', '5332737344', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(75, 'Itapema Transportes e Comércio', '', NULL, '', '5332211901', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(76, 'Lima Fretes', '', NULL, '', '53984293496', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(77, 'MJR Mudanças', '', NULL, '', '54992087355', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(78, 'Dione Santana', '', NULL, '', '53981262988', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(79, 'SM Fretes e Mudanças', '', NULL, '', '53991942678', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', 'RS', 'Pelotas', 0, NULL, NULL, NULL),
(82, 'José Carlos Echenique Soares Filho', 'jc.echenique@gmail.com', NULL, 'youtube', '53984051060', 'Carro', 'Segunda a Sexta', '', '', 'Não', 'RS', 'Pelotas', 0, '079a96cf107dbf80b3bb15df5bf888e40009e5fafaf9c184dfc7334d140eb2e8', '2025-09-04 17:30:48', NULL),
(87, 'Fretes Top 10', '', NULL, '$2y$10$/78ly5rzBk9zaCr9L1IGqOIkMxODrkeTDUNJh5gqHhvt9NLxAhohy', '53991726922', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(88, 'LFM Fretes e Mudanças', '', NULL, '$2y$10$AZqWvkak6LrJoLdGsnNcfuly4KihxgMZI9uU4JxwDv2EPzUpiLfLS', '48991618568', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(89, 'Disk Mudanças', '', NULL, '$2y$10$Dw7tGXOdH1uk2f/WBaROx.z6kKTGdnsHMhBXa8/jY3g/wEk8sudve', '51997950906', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(90, 'Tom Transportes', '', NULL, '$2y$10$YRZA7ak50yEJCdV5MljeDupe4yt7qEjiJ54MtPV2XXCKtN8TWsYKe', '53984793891', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(91, 'Vitor Fretes', '', NULL, '$2y$10$4drxlK6FOWjZ.l28fBFBhuJbfoA1KU9sYAmacOqEieDzz9Y4x5hra', '53984736813', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(92, 'Alô Fretes', '', NULL, '$2y$10$lTlFCdzvVnnF6zH4/QUQJuVysMRTfpdI4m3AaZTnkcoPF7Ca/wwVO', '53991064006', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(93, 'Transemerson', '', NULL, '$2y$10$eso9nQ7xDO7HC8GeXefSCe74rF48HVcdC6wAFdTkM3ZPIQXEp8Tra', '53984253396', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(94, 'Fretes Vuc', '', NULL, '$2y$10$BtWTgRoPDjb/pAvlvjr5quPvy/GAcyKbTAuRTgTJBPFaM9kjciC0.', '53992483333', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(95, 'Fredy Fretes', '', NULL, '$2y$10$8KEdq3qSj/S.MDM5iQu9lumzr7Ct2.yfU/eXH8ye5DQz7WrksE7ee', '53981256378', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(96, 'Paty Fretes', '', NULL, '$2y$10$gnklT4PBM1Lbzk88PAsEOO7BOwk9UifWhvMXAS45s4.1WT4lsb7VS', '53984256168', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(97, 'JD Transportes e Mudanças', '', NULL, '$2y$10$Jut0uNaH7.0Cc/z9uJGagO9vlRnWGR43F./H1ghtLWBS61xUIFxla', '53984555558', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(98, 'André Moraes', '', NULL, '$2y$10$WVYCcj30YLrO/P8f9b7R9eRn2.33ifDjjEF2AEMYXaJkMGBBKyLqe', '53999647605', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(99, 'Adilson Fretes', '', NULL, '$2y$10$BRfRtweCcRaD8COEU2fXqeteern8Lx/Og2cbCI0G2n6HqFG8M8AO.', '53984139638', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(100, 'Clóvis Fretes', '', NULL, '$2y$10$tMK00KCG6eppIvzflXptaulRnf9rhNwK53ufa9/nsVBqroeQreVX6', '53999818773', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(101, 'Cruz Frete', '', NULL, '$2y$10$Lj2hW3iXI2EWLPo0WHvVG.ycLfE1OEPvs7AN8rO7U71j7uVhopveK', '53991151094', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(102, 'Fretes Fábio', '', NULL, '$2y$10$4XnYa5Fdxoc18hhSXPs50.hzLFbgqiWjtErKKi4.EkRbkjczcvqKK', '53984190922', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(103, 'Rocha e Rocha Fretes', '', NULL, '$2y$10$37lLdSPL.CWJ7RNIpl43c.wlAhHuv8blJpDSQAoUIkoGJtD1AA4x6', '53984546417', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(104, 'Mudanças e Transporte Makoski', '', NULL, '$2y$10$Ip8bC9XR8ZVfCPNQV7mLeuSiJCu1Qn.FMe7073bf7/yG1U9mKIRQ2', '51999960740', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(105, 'Alexandre Fretes', '', NULL, '$2y$10$/Ci4U4gFycczpsdFfWoo5.t.nY9YD3xsXkoKXDhy0N8CDDThMhN/y', '53984535986', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(106, 'Frete Paulo', '', NULL, '$2y$10$SfxHndAplTqrifuzxvbfIOtpUOdoZuRqbxCAP7S7GR7xJkv/mxnBO', '53984314410', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(107, 'Maicon Farias', '', NULL, '$2y$10$9b/60s9NOLpkRfySHQ1l4eNmD7KDJDoPq3nhh/8CVAKeNoMC3gcum', '53991215590', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(108, 'Henrique Fretes', '', NULL, '$2y$10$7mMusLGLadPTmBHySWUw.u3iIB21rJEsXpeyLzIXfa0RQDhuoUa8W', '55996632283', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(109, 'JR Transportes', '', NULL, '$2y$10$0Zb7ZJXaWAJLPAqpMhCwL.SghvgkUOqb5lBF5ebjm0.frTZsABZJ2', '53984054324', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(110, 'Transportes Monteiro', '', NULL, '$2y$10$kvZq1J1yoGXWnTNprOVRHufUMgnf3Jyrzafbbt4tyssy7qfMrG.5u', '51995322231', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(111, 'Pampa Transportes', '', NULL, '$2y$10$cUbH8pmuJj5QDdBZchN9feOyFGzcLLE2BBVGADIatWo0NrBp3wlq6', '53991351620', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(112, 'Everton Sampaio', '', NULL, '$2y$10$/uqBUjwwgIA/gRVuh3f1/.oVcAE33YDJT5/oeXInux/JQHoi1H/VG', '53984429449', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(113, 'Loko Fretes', '', NULL, '$2y$10$1pH67JaQTX43YgOJWntGg.HqTumb9IG1fKDHPjkXGLsyffN8bkzX6', '53981316714', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(114, 'Diogo Fernandes', '', NULL, '$2y$10$wd243o1N7wCCdSUM/jVDdeHAu2IlpmfNjr8vEEbVTAi.atwS5wrF.', '53984689445', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(115, 'Ronaldo Munhoz', '', NULL, '$2y$10$Dg5tTyLuIVvdwIjG6UMAfeYzqVEcXzrjgXaXuvUdf/SOJj4YfCiNe', '53991221516', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(116, 'Fretes Braga', '', NULL, '$2y$10$snJIIYKYPCuwoHj.5EEcWeslr.UAWM4T4R5STZQNLMSNE3lcDCHiG', '53999024091', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(117, 'Parecida Transportes', '', NULL, '$2y$10$LpQxc32S.t5ZU/sLzu8vbecT4xdfxLj.Np6XFW.ojxlhnZxb7OXk2', '53984222222', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(118, 'Antônio Fretes', '', NULL, '$2y$10$vtD4uMiE/qVluBsmlA1Hp.ejj5xYbpVhgibKokIIAhmyXt5veDCVq', '53999522505', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(119, 'Frete Rápido Expresso', '', NULL, '$2y$10$L5Y7rZ2atY3ZL0jm2ysSf.zbzW5W7TTOw67QxV0LEhqi2Fi.3Xwoa', '53999660862', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(120, 'Leandro Silva', '', NULL, '$2y$10$ZZoDy.sbL2xnglOVuDr9XedLuyCHgBp7MQP97qw34jY5Pu/tS5K1i', '53984257373', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(121, 'Zeca Fretes', '', NULL, '$2y$10$UAPcKTs7Zt5oCUE.gi310OjSyJDL9K1nY01hZA5xuBeaM2.9a0Vou', '53984653119', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(122, 'FM Transportes', '', NULL, '$2y$10$E.oBvGGhbbf5qp97EbK3eeBpOjPiVnk53185ucXQdXQpEv3wT7xoW', '53981090948', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(123, 'Fretes e Entregas Pelotas', '', NULL, '$2y$10$0IBJuZN7Skv56Y5jm5EJwucKBDW5Hv1OTeWT0sRjSVrifYm01N4e.', '53991311270', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(124, 'Transportes Xavier', '', NULL, '$2y$10$1Y2YxuOIllIUHHKbQoRIT.cY5Tk1cN2JE50Wyh13O6JWXy5a1/FwS', '53984115556', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(125, 'MF Fretes e Viagens', '', NULL, '$2y$10$yjrOHWSNlW/k7ZnhvunmC.QBuwL.PsklIpXefHYlN9gd6VegjqYqW', '53999119029', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(126, 'Frete Simões', '', NULL, '$2y$10$L1PYO/7vQ1hxei0EI0SRou4jychqekRBTAVKqy96Nf7vq47qRT5V6', '53991088598', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(127, 'Frete Luiz', '', NULL, '$2y$10$X9JKS54o5q8yvpkSD07gNuZ1UsWs0Pr8KGu3/PebDYaMim9Msw0ti', '53984035455', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(128, 'Pediu Levou', '', NULL, '$2y$10$DumM9/qw9GOFrEvqKMAei.HwrU1DMFMeYyRzON6vipyo0fr88Ay4K', '53981166786', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(129, 'Marcelo Fretes', '', NULL, '$2y$10$aH19e9FVYyvcvfVngjjRrujiVe7WK/EAzy/SEHJAUED7bDlIx3YSC', '53984468349', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(130, 'K.B.Ludo Fretes', '', NULL, '$2y$10$d/sDEf635FfNgopPeB8vbuRjEYjAOffEN8HLtTsp/9uDqFmLdtFrq', '53984098475', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(131, 'Feliciano Fretes', '', NULL, '$2y$10$rZKhSTpi9H7Mji6vJwEL0uPFp9QLZNN7Bg7fQhAlYMXwFdzLWigaq', '53991264033', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(132, 'Frete 2 Irmãos', '', NULL, '$2y$10$QXIFefh/3H.hlEobTJVC2eUiQN1GrhyAAHTtNBOao.dEAbYC4Dkke', '53984730069', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(133, 'Leitão Fretes', '', NULL, '$2y$10$P58LcUzX7.mB9dR6ftHcvu.f6DnlT75K.Lr3x2ichNzyT9zKgjGPy', '53999994019', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(134, 'Igor Fretes', '', NULL, '$2y$10$MjYvTPdYofNsp8f.5hQgieVsDtdzHTOWPbrfZBWlEMdSZ9Yjx35FW', '53991033990', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(135, 'Fretes Rodrigues', '', NULL, '$2y$10$yEuUYdj2KoJDwzCkiJ7O1OQ3kYaTJlpAckTX02cCuRa6J55z.h2bS', '53981188376', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(136, 'Érico Cargas e Mudanças', '', NULL, '$2y$10$M.OyrhmkhmOFqkThQw52h./v3cVkYpZkL8DUN60lAz.y1WeOMcOQ.', '53991290402', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(137, 'Elwis Fretes', '', NULL, '$2y$10$C2nr1Alz1eadSOLrXTvveuFk.Rr8nyYX7dp943jy7f7xZ/YCpdgDe', '53984255318', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(138, 'Tiliga Transporte de Animais e Fretes Gerais', '', NULL, '$2y$10$.eMHA3.QHBsrhekv392dZejKTeap7zF3PoUlNP4hpgyxCSPkPjgxa', '53984151048', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', 'Transporte de Animais', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(139, 'Simba Transportes', '', NULL, '$2y$10$tU1VlcD1WVqpCzkNMVCRH.3j04OcppHMizVcoIWXO8wEga5rr6M7m', '62991293197', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(140, 'Pequenos Fretes', '', NULL, '$2y$10$Qf1ihx8S6smPqdMubA/SOuCiX9tMV47g/AaYoBwngCwK40tjAgRcy', '53984545804', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(141, 'Fretes Madruga', '', NULL, '$2y$10$ETe85yNswrErRlSS9.SpD.Pk8l7AgOnytmNgqKE7NMXvg9SO3NDEW', '53984290410', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(142, 'Transcef', '', NULL, '$2y$10$6I/206n38bogd2B7EJfA/OuCxdOTVWRR3DFLMQehZeRDM39aEGaH6', '5333073332', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(143, 'Marcos Transportes', '', NULL, '$2y$10$1ocMS9Zda9P6IFl4rZUikuns8vkud1QRc7rAJvPEfeednkmbcOmdS', '53991723034', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(144, 'Paulinho Mudanças', '', NULL, '$2y$10$JIEFw33CB1gddFIVY6Mhy.SO4bqmawJ3tQhDyxWPfIbAuoq26w3wi', '53984771108', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(145, 'Frete Senhora', '', NULL, '$2y$10$woQr.d8fKJr.b9oZTmYIzu9skZFCTVJ/kDCDXOXHZmsIa.NUSP6be', '53991888126', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(146, 'S.O.S. Mudança', '', NULL, '$2y$10$Nvqy9VfT4o5hnGybqQtTh.gBtUNonjFEpycBnnrhbCQm.0TW81xui', '53984225216', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(147, 'Transportadora Vapt Vupt', '', NULL, '$2y$10$VHKqD54Hqxr23uwA7NDv0.nZM50.TFxizvM1XStY0ot1qLkxUwk3G', '5332787000', 'Camionete', 'Segunda a Sexta', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(148, 'Fliegel Transportes', '', NULL, '$2y$10$znKkMHFHJIt8kU/djcOeq.29qWWLMw4xCcjWvCM2en5KC7/OzsPia', '53984095051', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(149, 'São João Encomendas', '', NULL, '$2y$10$ZoWX/I3qoC7vEPoc8LHbk.7bEgOAjyEULViFPGqSKsVZQUbsU9kBm', '5332222760', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(150, 'Cargo Express', '', NULL, '$2y$10$A.u0urZgFTY4tKwXWQ3k5OG5z/13QLiBJcIX/2zNluFBPv1n4mg06', '53981164286', 'Camionete', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(151, 'Patrick Lima', '', NULL, '$2y$10$NxtwpeSgESfthpKkq8wOZ.ClfcdcSL26jNCQEZRA.Zv9cO14y18Ei', '53984422375', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(152, 'Santa Cruz Fretes', '', NULL, '$2y$10$N2n17RF582uNVqq.jDrA3OYEh.iwSoBg2QfWFtfHurwognoJaSfAS', '5332734399', 'Caminhão', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(153, 'Kokasul Fretes', '', NULL, '$2y$10$C9sL8trr.T28hHscCqBMW.ugDXkcJd/22mvI0xG4TAz01ti8Tjt6q', '5332615724', 'Caminhão', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(154, 'Diego Fretes', '', NULL, '$2y$10$zMJHk97kt3tbAeLerPdFp.CKuJ6FDoAESZ4sZDghnw9A23TlvZhKG', '53984460625', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(155, 'Carlos Fretes', '', NULL, '$2y$10$p3Y429wPgK3vpQmD30ZxMOW5eCNZz04dsHWe/W4ChXi705AwzEzmG', '53991761173', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(156, 'Almeida Fretes', '', NULL, '$2y$10$cFz8gBSN.QzPv7kadIhuzO0bCD.Liq.eGfsqVS4aOWXwkK/Bm6mqe', '53981131496', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(157, 'Mikrosul Logística', '', NULL, '$2y$10$AdwtrSgClf8a3ocQu91FEOPBKKzRbrtlzPLwnHQ4.HeK//SogzG8S', '5332831818', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(158, 'Multitrans', '', NULL, '$2y$10$G/MFyyhag.hJnYAs3Gzod.vEK5/tVQEX2A7ZugWEE2M9bipbzuYB2', '55533261654', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(159, 'Transportes São Jorge', '', NULL, '$2y$10$uCcQad9ex72fET6KAKORvuR0vzcGtAD9PXL7jo8StWLyzs5qPaWGm', '5332281761', 'Camionete, Caminhão', 'Segunda a Sexta, Sábado', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(160, 'Juliana Fonseca', '', NULL, '$2y$10$s809fXoJtAUISIORghti8e/1UI2HSbcfpP57nic4.dIrLZnr8o75e', '53981111420', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(161, 'Ricardo Fretes', '', NULL, '$2y$10$ZGVuIvfzz1bwpy8n7Cnu9e5oTzIBcZgK0p4xb3tf9wLi/52soPpgi', '53991389014', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(162, 'Pedro Fretes', '', NULL, '$2y$10$MLNMCGFV.aYcQ0N4IJM8uOtN/hYtYDwrROg06utMAcx742pohZG1K', '53984237655', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(163, 'Fretes Maninho', '', NULL, '$2y$10$v63Hrx7GennAvk6RYpKjOunQJbBE1EdUiZ/8B4SjG0puYTcZsslyi', '53991072371', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(164, 'Marcos Frete', '', NULL, '$2y$10$f8jRliL2apXMqG1gvekZ4OIhccykDUc5ObyNjP90e.zm5bREo46Zm', '53991296663', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(165, 'Frete Eloi', '', NULL, '$2y$10$MLpozgo856gfVYvBd.RM5Oyz9YlCa3c5Ph9OMZ8zM4D/.aiAlx/LS', '53991936358', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(166, 'Ferraçosul Freteiro', '', NULL, '$2y$10$726Jy4zevbfpX5fm16F04OOpd9N6mGXQF20Hlo0uPZuVJ1ohQG1TW', '53984030366', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(167, 'Gabriel Nunes', '', NULL, '$2y$10$yiLbHwPWa4XwJpbQONVITuf98h2wo5TpAkRwOiga6LNlIOg8y8BTW', '53984124526', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(168, 'SS Transportes e Mudanças', '', NULL, '$2y$10$CkjdUusYg/RTvDIKWQty4O5Mvyxc2/qbm6A5dpm7eTJU35aLk1w4a', '53984480596', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(169, 'Expresso Melo Transportes', '', NULL, '$2y$10$4cMVnGB2YbnFAh2p3zwG7uSXbbxtqSHmTdIRNTo/GRUSLhKBxbzK6', '53991561696', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(170, 'MC Transportes', '', NULL, '$2y$10$iPEIbRH6.3vNPMR9xY7eNetPcl2fsHTKEum2tqz6.30OWPYJFVJx2', '53981219564', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(171, 'Alemão Express', '', NULL, '$2y$10$G23wvvrvf1mE3KuxxBuH2.G.but9uKHqKVWjGPGV.BeSG71NsASmi', '53984380955', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(172, 'Marcelo Pires', '', NULL, '$2y$10$ukqkUsyjXxtkG15Rf0emKORR.aWecLEu7oCrBzo8Z1kM9ulk8fery', '53984292015', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(173, 'Ricardo Essenza', '', NULL, '$2y$10$cK1WGhv9PrRYQVxzZDAPHu/WaSSR6sG/gYDrDMj0ZaXSyFijfGXVy', '53984661395', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(174, 'Márcio Garcez Severo', '', NULL, '$2y$10$Qd42uA6/FOUiPRZ70mvkc.FQgujPPCj1rlCEkF6O3uutwxdZMPTay', '53991011001', 'Carro', 'Segunda a Sexta, Sábado', 'Municipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(175, 'João Antônio de Oliveira Júnior', '', NULL, '$2y$10$AlYN6HLVJ24D8KVSMnMxxuYslo4iDwCmB.HRxwmS9GMkvAk3rV1Nm', '11964932826', 'Camionete', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal', '', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(176, 'João Marcelo Betinelli', '', NULL, '$2y$10$qLYgE8gMz2BKQ2UPgojcaOlshP.FQYH0f2abdllD.XM38fU9L.bUm', '54993551315', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(177, 'Jéssica Noronha da Rosa Alves', '', NULL, '$2y$10$z0qIvzgvzrsn.hwNo6VAeOlq5zBSdCa4WqRmkXnus8.12fj4nmJlm', '53991180838', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(178, 'Catiucia Gonçalves', '', NULL, '$2y$10$0afOanYE5FY.RMZ80DT1hO2vUWssJopQ.nLqjhLAkqwEm2Hiy835S', '53991392739', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(179, 'Vinícius Batista Fagundes', '', NULL, '$2y$10$AnMGUW6vRtOlcdqAxkX2d.3A2QJ7hBZ6RMk3Glcmhaa1TZ11hJJNm', '53981497359', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(180, 'Bruna dos Santos Specht', '', NULL, '$2y$10$ADcnbTIjcEz1U0mHR2bGtu4zWqNAxppsaKLBjj4R6FoCLU3v.oHeO', '53999032191', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(181, 'Moisés Camargo', '', NULL, '$2y$10$5yGF.Wnj0H9nth8Nb1NZN.K7pVDPgG4kxuQnGdlxSSqQoBzijp6Qa', '41984412160', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(182, 'Rogério Zanetti', '', NULL, '$2y$10$/G2p9h2TXUtchXeCoFF5Yu3n23uZok58Hy4kE2kgrRP2a8ApFccMu', '53999212671', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(183, 'Matheus Fretes', '', NULL, '$2y$10$YCT983DnueK2w8RlMvX1meixSp6taarO2jWfymTvXoZ/uAYGxp.Zu', '53984280884', 'Caminhão', 'Segunda a Sexta, Sábado, Domingo, Feriados', 'Municipal, Intermunicipal, Interestadual', '', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(184, 'Guilherme Teste', 'dietrichcustodio@gmail.com', NULL, '$2y$10$NjDgxwtZP5mmWF8CODg8vulfVX0jaX9HE2vNTF7aaYRjP/aBTCcjy', '53999999998', 'Carro', 'Segunda a Sexta', 'Municipal', 'Empacotamento', 'Sim', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(185, 'Guilherme', 'guilhermedietrich@outlook.com', NULL, '$2y$10$0w7/cEMiwIT84ZrTwzn5dOfAGIc7oazBFrH6ymx21UHnJ4ljIirFq', '53999999997', 'Carro', 'Segunda a Sexta', 'Municipal', 'Empacotamento', 'Não', NULL, 'Pelotas', 0, NULL, NULL, NULL),
(186, 'VERNER JAHNKE', 'vernerjahnke@gmail.com', NULL, '$2y$10$N/mZJ.hvYQ4SqDXNEgq.P.f//p9icd2fvgzGTwks.bwoa/KLDVPA2', '53981144596', 'Caminhão', 'Segunda a Sexta, Sábado', 'Interestadual', '', 'Não', NULL, '', 0, NULL, NULL, NULL);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `cadastro_fretes`
--
ALTER TABLE `cadastro_fretes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cadastro_fretes`
--
ALTER TABLE `cadastro_fretes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=187;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
