# AgendaWebFinalizada

Banco de dados da Agenda:

drop database `tasklist`;
CREATE DATABASE `tasklist`;
USE tasklist;

CREATE TABLE `usuarios` (
`id` int(11) NOT NULL AUTO_INCREMENT ,
`login` varchar(250) NOT NULL,
`senha` varchar(250) NOT NULL,
`nome` varchar(250) NOT NULL,
`email` varchar(250) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tarefas` (
`idTarefa` int(11) NOT NULL AUTO_INCREMENT ,
`titulo` varchar(250) NOT NULL,
`descricao` varchar(250) DEFAULT NULL,
`data_criacao` date DEFAULT CURRENT_TIMESTAMP,
`data_conclusao` date DEFAULT NULL,
`status` varchar(250) DEFAULT 'To Do',
`idUser` int(11) DEFAULT NULL,
PRIMARY KEY (`idTarefa`),
FOREIGN KEY (`idUser`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SELECT * FROM tarefas;
SELECT * FROM usuarios;
