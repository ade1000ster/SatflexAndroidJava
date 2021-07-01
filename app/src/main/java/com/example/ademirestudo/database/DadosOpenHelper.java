package com.example.ademirestudo.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import model.modelCategorias;
import model.modelDocumento;
import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;
import model.modelParametros;
import model.modelProdutos;

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 58;
    private static final String BANCO_CLIENTE = "satflex";
    String teste;
    String test2;
    public DadosOpenHelper(Context context){
        super( context,  BANCO_CLIENTE, null, VERSAO_BANCO );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL( "CREATE TABLE IF NOT EXISTS documentopagamento(iddocumentopagamento INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "iddocumento integer NOT NULL,"+
                "idformapagamento integer NOT NULL,"+
                "totalpagamento numeric(12,2) NOT NULL,"+
                "FOREIGN KEY (iddocumento) REFERENCES documento (iddocumento),"+
                "FOREIGN KEY (idformapagamento) REFERENCES formapagamento (idformapagamento))");





        db.execSQL( "CREATE TABLE IF NOT EXISTS formapagamento (idformapagamento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(50) NOT NULL," +
                "especie character(2) NOT NULL,"+
                "status character(1) NOT NULL DEFAULT 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (1, '2017-03-06 11:49:44.086728', 'Dinheiro', '01', 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (2, '2017-03-06 11:49:44.086728', 'Cheque', '02', 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (3, '2017-03-06 11:49:44.086728', 'Cartão de crédito', '03', 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (4, '2017-03-06 11:49:44.086728', 'Cartão de débito', '04', 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (5, '2017-03-06 11:49:44.086728', 'Crédito loja', '05', 'I')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (6, '2017-03-06 11:49:44.086728', 'Vale alimentação', '10', 'A')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (7, '2017-03-06 11:49:44.086728', 'Vale refeição', '11', 'I')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (8, '2017-03-06 11:49:44.086728', 'Vale presente', '12', 'I')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (9, '2017-03-06 11:49:44.086728', 'Vale combustível', '13', 'I')");
        db.execSQL("INSERT INTO formapagamento (idformapagamento, dthrcriacao, descricao, especie, status) VALUES (10, '2017-03-06 11:49:44.086728', 'Outros', '99', 'A')");



        db.execSQL( "CREATE TABLE IF NOT EXISTS ncm ( idncm INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "codigoncm varyingcharacter(10) NOT NULL," +
                "descricao varyingcharacter(200) NOT NULL," +
                "origem character(1) NOT NULL DEFAULT '0',"+
                "csosn character(3) NOT NULL DEFAULT '102',"+
                "aliqicms numeric(8,2) NOT NULL DEFAULT 0,"+
                "cstpis character(2) NOT NULL DEFAULT '49',"+
                "aliqpis numeric(8,2) NOT NULL DEFAULT 0,"+
                "cstcofins character(2) NOT NULL DEFAULT '49',"+
                "aliqcofins numeric(8,2) NOT NULL DEFAULT 0,"+
                "codcontribsocial character(2) NOT NULL DEFAULT '01',"+
                "cest varyingcharacter(10),"+
                "cfop varyingcharacter(5) NOT NULL DEFAULT '5.102')" );  //DEFAULT '99'

        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (1, '2017-03-06 12:55:44.133051', '0000.00.00', 'Teste', '0', '102', 0, '49', 0, '49', 0, '00', NULL, '5.102')");



        db.execSQL("CREATE TABLE IF NOT EXISTS categoria (idcategoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(100) NOT NULL UNIQUE COLLATE NOCASE,"+
                "  padrao character(1) NOT NULL DEFAULT 'N'," +
                "  idncm integer NOT NULL DEFAULT 0,"+       //DEFAULT 0
                "  sinctributacao character(1) NOT NULL DEFAULT 'S'," +
                "  origem character(1) NOT NULL DEFAULT '0'," +
                "  csosn character(3) NOT NULL DEFAULT '102'," +
                "  aliqicms numeric(8,2) NOT NULL DEFAULT 0," +
                "  cstpis character(2) NOT NULL DEFAULT '49'," +
                "  aliqpis numeric(8,2) NOT NULL DEFAULT 0," +
                "  cstcofins character(2) NOT NULL DEFAULT '49'," +
                "  aliqcofins numeric(8,2) NOT NULL DEFAULT 0," +
                "  codcontribsocial character(2) NOT NULL DEFAULT '01'," +
                "  cor varyingcharacter(50) NOT NULL," +
                "  cest varyingcharacter(10)," +
                "  cfop varyingcharacter(5) NOT NULL DEFAULT '5.102',"+    //DEFAULT '99'
                "FOREIGN KEY (idncm) REFERENCES ncm (idncm))");


        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (1, '2019-08-09 14:39:18', 'CATEGORIA TESTE', 'N', 1, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#FF4500', NULL, '5.102')");





        db.execSQL("CREATE TABLE IF NOT EXISTS produto ( idproduto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(100) NOT NULL UNIQUE COLLATE NOCASE,"+
                "idcategoria integer NOT NULL ,"+
                "preco numeric(12,2) NOT NULL DEFAULT 0,"+
                "codigoean varyingcharacter(20),"+
                "precovariavel character(1) NOT NULL DEFAULT 'N',"+
                "favorito character(1) NOT NULL DEFAULT 'N',"+
                "sinctributacao character(1) NOT NULL DEFAULT 'S',"+
                "idncm integer NOT NULL DEFAULT 0,"+       //DEFAULT 0
                "origem character(1) NOT NULL DEFAULT '0',"+
                "csosn character(3) NOT NULL DEFAULT '102',"+
                "aliqicms numeric(8,2) NOT NULL DEFAULT 0,"+
                "cstpis character(2) NOT NULL DEFAULT '49',"+
                "aliqpis numeric(8,2) NOT NULL DEFAULT 0,"+
                "cstcofins character(2) NOT NULL DEFAULT '49',"+
                "aliqcofins numeric(8,2) NOT NULL DEFAULT 0,"+
                "codcontribsocial character(2) NOT NULL DEFAULT '01',"+
                "cest varyingcharacter(10),"+
                "cfop varyingcharacter(5) NOT NULL DEFAULT '5.102',"+    //DEFAULT '99'
                "status character(1) NOT NULL DEFAULT 'A',"+
                "idunidade integer NOT NULL DEFAULT 0,"+//DEFAULT 0
                "FOREIGN KEY (idcategoria) REFERENCES categoria (idcategoria)," +
                "FOREIGN KEY (idncm) REFERENCES ncm (idncm),"+
                "FOREIGN KEY (idunidade) REFERENCES unidade (idunidade))");


        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (2, '2017-03-08 12:12:24.30624', 'PRODUTO TESTE', 1, 5, '0', 'S', 'N', 'N', 1, '0', '102', 0, '49', 0, '49', 0, '00', '00.000.00', '5.102', 'A', 1)");


        db.execSQL(" CREATE TABLE IF NOT EXISTS documento (iddocumento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "dthrinicial DATE DEFAULT (datetime('now','localtime'))," +
                "dthrfinal DATE DEFAULT (datetime('now','localtime'))," +
                "status CHARACTER(1) NOT NULL DEFAULT 'A'," +
                " modelo character(2)," +
                "chave varyingcharacter(50)," +
                "cpfcnpj varyingcharacter(20)," +
                "numeroprodutos integer NOT NULL DEFAULT 0," +
                "totalquantidade numeric(12,3) NOT NULL DEFAULT 0," +
                "totaldesconto numeric(12,2) NOT NULL DEFAULT 0," +
                "totaldocumento numeric(12,2) NOT NULL DEFAULT 0," +
                "baseicms numeric(12,2) NOT NULL DEFAULT 0," +
                "totalicms numeric(12,2) NOT NULL DEFAULT 0," +
                " basepis numeric(12,2) NOT NULL DEFAULT 0," +
                "totalpis numeric(12,2) NOT NULL DEFAULT 0," +
                "basecofins numeric(12,2) NOT NULL DEFAULT 0," +
                "totalcofins numeric(12,2) NOT NULL DEFAULT 0," +
                "xml text," +
                "dthrcancelamento DATE DEFAULT (datetime('now','localtime'))," +
                "xmlcanc text," +
                "chavecanc varyingcharacter(50)," +
                "numero integer," +
                "totaltroco numeric(12,2) NOT NULL DEFAULT 0," +
                "idparceiro integer," +
                "operacao character(2) NOT NULL," +
                "serie varyingcharacter(3)," +
                "protocolo  varyingcharacter(50)," +
                "nomeorcamento varyingcharacter(100)," +
                " totalacrescimo numeric(12,2) NOT NULL DEFAULT 0," +
                " protocolocanc varyingcharacter(50)," +
                "observacao text," +
                "modfrete integer NOT NULL DEFAULT 9," +
                "idparceirotransp integer," +
                "pesobruto numeric(12,3)," +
                "pesoliquido numeric(12,3)," +
                "qtdevolume integer," +
                "chaveref varyingcharacter(50),"+
                "FOREIGN KEY (idparceiro) REFERENCES parceiro (idparceiro))");


        db.execSQL("CREATE TABLE IF NOT EXISTS documentoproduto ( iddocumentoproduto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "iddocumento integer NOT NULL  DEFAULT 0,"+  // DEFAULT 0
                "sequencial integer NOT NULL  DEFAULT 0," +  // DEFAULT 0
                "idproduto integer NOT NULL  DEFAULT 0," +  // DEFAULT 0
                "descricao varyingcharacter(100) NOT NULL,"+
                "preco numeric(12,2) NOT NULL DEFAULT 0,"+
                "quantidade numeric(12,3) NOT NULL DEFAULT 0,"+
                "descontounitario numeric(12,2) NOT NULL DEFAULT 0,"+
                "totaldesconto numeric(12,2) NOT NULL DEFAULT 0,"+
                "totalproduto numeric(12,2) NOT NULL DEFAULT 0,"+
                "codigoncm varyingcharacter(10) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "origem character(1) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "csosn character(3) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "aliqicms numeric(8,2) NOT NULL DEFAULT 0,"+
                "baseicms numeric(12,2) NOT NULL DEFAULT 0,"+
                "totalicms numeric(12,2) NOT NULL DEFAULT 0,"+
                "cstpis character(2) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "aliqpis numeric(8,2) NOT NULL DEFAULT 0,"+
                "basepis numeric(12,2) NOT NULL DEFAULT 0,"+
                "totalpis numeric(12,2) NOT NULL DEFAULT 0,"+
                "cstcofins character(2) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "aliqcofins numeric(8,2) NOT NULL DEFAULT 0,"+
                "basecofins numeric(12,2) NOT NULL DEFAULT 0,"+
                "totalcofins numeric(12,2) NOT NULL DEFAULT 0,"+
                "codcontribsocial character(2) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
                "cest varyingcharacter(10) NOT NULL DEFAULT '0',"+
                "cfop varyingcharacter(5) NOT NULL DEFAULT '99',"+
                "acrescimounitario numeric(12,2) NOT NULL DEFAULT 0,"+
                "totalacrescimo numeric(12,2) NOT NULL DEFAULT 0,"+
                "FOREIGN KEY (idproduto) REFERENCES produto (idproduto),"+
                "FOREIGN KEY (iddocumento) REFERENCES documento (iddocumento))");

        db.execSQL("CREATE TABLE IF NOT EXISTS unidade ( idunidade INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(50) NOT NULL,"+
                "sigla varyingcharacter(3) NOT NULL,"+
                "precisao integer NOT NULL  DEFAULT 0)");  // DEFAULT 0

        ContentValues unidade = new ContentValues(  );
        ContentValues kilograma = new ContentValues(  );
        ContentValues metro = new ContentValues(  );

        unidade.put( "descricao", "Unidade" );
        unidade.put( "sigla", "UN");
        unidade.put( "precisao", 0);
        db.insert( "unidade" ,null, unidade  );

        kilograma.put( "descricao", "Kilograma" );
        kilograma.put( "sigla", "KG");
        kilograma.put( "precisao", 3);
        db.insert( "unidade" ,null, kilograma  );

        metro.put( "descricao", "Metro" );
        metro.put( "sigla", "M");
        metro.put( "precisao", 2);
        db.insert( "unidade" ,null, metro  );


        db.execSQL("CREATE TABLE IF NOT EXISTS formapagamento ( idformapagamento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(50) NOT NULL,"+
                "especie character(2) NOT NULL,"+
                "status CHARACTER(1) NOT NULL DEFAULT 'A')" );  // DEFAULT A

        db.execSQL("CREATE TABLE IF NOT EXISTS estado (idestado INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        "dthrcriacao DATE DEFAULT (datetime('now','localtime')),"+
                        "codigooficial integer NOT NULL,"+
                        "sigla character(2) NOT NULL,"+
                "nome  varyingcharacter(100) NOT NULL)");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (1, '2017-05-16 08:55:46.292255', 11, 'RO', 'Rondônia')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (2, '2017-05-16 08:55:46.292255', 12, 'AC', 'Acre')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (3, '2017-05-16 08:55:46.292255', 13, 'AM', 'Amazonas')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (4, '2017-05-16 08:55:46.292255', 14, 'RR', 'Roraima')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (5, '2017-05-16 08:55:46.292255', 15, 'PA', 'Pará')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (6, '2017-05-16 08:55:46.292255', 16, 'AP', 'Amapá')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (7, '2017-05-16 08:55:46.292255', 17, 'TO', 'Tocantins')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (8, '2017-05-16 08:55:46.292255', 21, 'MA', 'Maranhão')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (9, '2017-05-16 08:55:46.292255', 22, 'PI', 'Piauí')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (10, '2017-05-16 08:55:46.292255', 23, 'CE', 'Ceará')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (11, '2017-05-16 08:55:46.292255', 24, 'RN', 'Rio Grande do Norte')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (12, '2017-05-16 08:55:46.292255', 25, 'PB', 'Paraíba')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (13, '2017-05-16 08:55:46.292255', 26, 'PE', 'Pernambuco')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (14, '2017-05-16 08:55:46.292255', 27, 'AL', 'Alagoas')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (15, '2017-05-16 08:55:46.292255', 28, 'SE', 'Sergipe')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (16, '2017-05-16 08:55:46.292255', 29, 'BA', 'Bahia')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (17, '2017-05-16 08:55:46.292255', 31, 'MG', 'Minas Gerais')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (18, '2017-05-16 08:55:46.292255', 32, 'ES', 'Espírito Santo')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (19, '2017-05-16 08:55:46.292255', 33, 'RJ', 'Rio de Janeiro')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (20, '2017-05-16 08:55:46.292255', 35, 'SP', 'São Paulo')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (21, '2017-05-16 08:55:46.292255', 41, 'PR', 'Paraná')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (22, '2017-05-16 08:55:46.292255', 42, 'SC', 'Santa Catarina')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (23, '2017-05-16 08:55:46.292255', 43, 'RS', 'Rio Grande do Sul')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (24, '2017-05-16 08:55:46.292255', 50, 'MS', 'Mato Grosso do Sul')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (25, '2017-05-16 08:55:46.292255', 51, 'MT', 'Mato Grosso')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (26, '2017-05-16 08:55:46.292255', 52, 'GO', 'Goiás')");
        db.execSQL("INSERT INTO estado (idestado, dthrcriacao, codigooficial, sigla, nome) VALUES (27, '2017-05-16 08:55:46.292255', 53, 'DF', 'Distrito Federal')");

        db.execSQL("CREATE TABLE IF NOT EXISTS  cidade (idcidade INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime')),"+
                        "idestado integer NOT NULL,"+
                        "codigooficial integer NOT NULL,"+
                        "nome varyingcharacter(100) NOT NULL,"+
                "FOREIGN KEY (idestado) REFERENCES estado (idestado))");

        db.execSQL("INSERT INTO cidade (idcidade, dthrcriacao, idestado, codigooficial, nome) VALUES (1, '2017-05-16 08:55:46.292255', 1, 110001, 'Alta Floresta D''Oeste')");
        db.execSQL("CREATE TABLE IF NOT EXISTS parametro ( idparametro INTEGER NOT NULL," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "nome varyingcharacter(50) NOT NULL,"+
                "grupo varyingcharacter(50) NOT NULL,"+
                "valor varyingcharacter(200) NOT NULL,"+
                "observacao varyingcharacter(200) NOT NULL,"+
                "visivel character(1),"+
                "ordem integer)");

        db.execSQL("CREATE TABLE IF NOT EXISTS parceiro(idparceiro integer  PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime')),"+
                        "nome varyingcharacter(200) NOT NULL,"+
                        "tipopessoa character(1) NOT NULL,"+
                        "cpfcnpj varyingcharacter(20) NOT NULL,"+
                        "inscricaoestadual varyingcharacter(50),"+
                        "contribuinteicms character(1) NOT NULL,"+
                        "idcidade integer NOT NULL,"+
                        "cep varyingcharacter(10) NOT NULL,"+
                        "bairro varyingcharacter(100) NOT NULL,"+
                        "endereco varyingcharacter(100) NOT NULL,"+
                        "numero varyingcharacter(10) NOT NULL,"+
                        "complemento varyingcharacter(100),"+
                        " email varyingcharacter(200),"+
                        "FOREIGN KEY (idcidade) REFERENCES cidade (idcidade))");

        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (22, '2017-03-06 11:49:44.086728', 'INFORMATIVO', 'REVENDA', 'A Remarca automação é a empresa responsável pelo desenvolvimento e suporte ao SAT-Flex. Teremos um grande prazer em atender você via telefone ou uma visita presencial. Caso precise de ajuda, por favor, entre em contato conosco. www.remarca.net', 'Texto informativo sobre a empresa revendedora do sistema.\nEsse texto estará disponível na tela de suporte ao cliente.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (28, '2017-03-06 11:49:44.086728', 'COLUNAS', 'IMPRESSORA', '48', 'Quantidade de colunas da impressora.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (14, '2017-03-06 11:49:44.086728', 'CAIXA', 'SAT', '001', 'Número do caixa onde se encontra instalado o equipamento fiscal SAT.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (26, '2017-03-06 11:49:44.086728', 'NOME', 'REVENDA', 'Remarca automação', 'Nome da revendedora do sistema.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (3, '2017-03-06 11:49:44.086728', 'UF', 'DESENVOLVEDORA', 'SP', 'União Federativa da desenvolvedora da aplicação comercial. (Exemplo: SP)', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (5, '2017-03-06 11:49:44.086728', 'ASSINATURA', 'DESENVOLVEDORA', 'SGR-SAT SISTEMA DE GESTAO E RETAGUARDA DO SAT', 'Assinatura digital da desenvolvedora.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (23, '2017-03-06 11:49:44.086728', 'ENDERECO', 'REVENDA', ' Largo da Matriz Velha, 69 - Freguesia do Ó, São Paulo - SP, 02925-060', 'Endereço da revendedora do sistema.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (10, '2017-03-06 11:49:44.086728', 'COMPLEMENTO', 'EMITENTE', 'Rua Pio XI', 'Complemento do endereço do emitente dos documentos fiscais.', 'S', 11)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (2, '2017-03-06 11:49:44.086728', 'RAZAOSOCIAL', 'EMITENTE', 'REMARCA AUTOMAÇÃO COMERCIAL LTDA', 'Razão social do emitente dos documentos fiscais.', 'S', 2)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (16, '2017-03-06 11:49:44.086728', 'UF', 'EMITENTE', 'SP', 'União Federativa do emitente dos documentos fiscais.', 'S', 6)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (12, '2017-03-06 11:49:44.086728', 'MUNICIPIO', 'EMITENTE', 'SÃO PAULO', 'Municipio do emitente dos documentos fiscais.', 'S', 7)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (11, '2017-03-06 11:49:44.086728', 'BAIRRO', 'EMITENTE', 'ALTO DA LAPA', 'Bairro do emitente dos documentos fiscais.', 'S', 8)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (8, '2017-03-06 11:49:44.086728', 'ENDERECO', 'EMITENTE', 'RUA PIO XI', 'Endereço do emitente dos documentos fiscais.\nNúmero e complemento de endereço ficam em outros parâmetros.', 'S', 9)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (9, '2017-03-06 11:49:44.086728', 'NUMERO', 'EMITENTE', '576', 'Número do endereço do emitente dos documentos fiscais.', 'S', 10)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (20, '2017-03-06 11:49:44.086728', 'IMPOSTOESTADUAL', 'EMITENTE', '2,30', 'Alíquota média de impostos estaduais.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (19, '2017-03-06 11:49:44.086728', 'IMPOSTOFEDERAL', 'EMITENTE', '3,20', 'Alíquota média de impostos federais.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (13, '2017-03-06 11:49:44.086728', 'CEP', 'EMITENTE', '04104-040', 'CEP do emitente dos documentos fiscais.', 'S', 5)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (33, '2017-03-16 15:14:54.145404', 'NOMEFANTASIA', 'EMITENTE', 'REMARCA AUTOMAÇÃO COMERCIAL LTDA', 'Nome fantasia do emitente dos documentos fiscais.', 'S', 1)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (46, '2018-11-19 11:51:11.85', 'DATAULTIMACOM', 'SISTEMA', '00/00/00', 'Endereço de IP do FTP de onde será efetuado backup do banco de dados.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (35, '2018-11-19 11:51:02.022', 'REINICIAREQUIPAMENTO', 'DIVERSOS', '0', 'HORA PARA REINICIAR O EQUIPAMENTO\nINFORMAR VALORES ENTRE 0 E 23\nPADRÃO 0 = MEIA NOITE', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (42, '2018-11-19 11:51:03.822', 'BOTAO10PORCENTO', 'DIVERSOS', 'N', 'Habilita o botão de acesso rápido para incluir a taxa de 10% de serviço.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (36, '2018-11-19 11:51:02.133', 'MUNICIPIOIBGE', 'NOTAFISCAL', '355030', 'Código do município do emitente de acordo com a tabela IBGE.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (43, '2018-11-19 11:51:03.904', 'ATIVO', 'NOTAFISCAL', 'N', 'Habilita o módulo de nota fiscal.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (38, '2018-11-19 11:51:02.286', 'CAMINHOCERTIFICADO', 'NOTAFISCAL', '', 'Caminho completo ate o arquivo do certificado digital do emitente das notas fiscais.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (47, '2018-11-19 11:51:11.85', 'PORTA', 'BACKUP', '1', 'Porta do FTP de onde será efetuado backup do banco de dados.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (44, '2018-11-19 11:51:11.372', 'NUMERO', 'NOTAFISCAL', '1', 'Número da próxima nota fiscal a ser transmitida.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (41, '2018-11-19 11:51:02.614', 'CONFIRMACAO', 'IMPRESSORA', 'N', 'Exigir confirmação do usuário para imprimir o cupom.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (51, '2018-11-19 11:51:12.381', 'SENHACERTIFICADO', 'NOTAFISCAL', '', 'Senha do certificado digital utilizado para transmissão da NF-e.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (45, '2018-11-19 11:51:11.761', 'LAYOUTVENDAS', 'DIVERSOS', '0', 'Layout da tela de vendas:\n0 - Pequena quantidade de produtos e categorias\n1 - Média quantidade de produtos e categorias\n2 - Grande quantidade de produtos e categorias', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (50, '2018-11-19 11:51:12.317', 'AMBIENTE', 'NOTAFISCAL', '2', 'Tipo de ambiente da NFe:\n1 - Produção\n2 - Homologação', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (48, '2018-11-19 11:51:11.85', 'USUARIO', 'BACKUP', 'satflex-backup', 'Usuário de acesso ao FTP de onde será efetuado backup do banco de dados.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (49, '2018-11-19 11:51:11.85', 'SENHA', 'BACKUP', 'automacao', 'Senha de acesso ao FTP de onde será efetuado backup do banco de dados.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (30, '2017-03-06 11:49:44.086728', 'FONTE', 'IMPRESSORA', '0', 'Tipo de fonte da impressora.\nEpson T-TM20: 0 (64 colunas), 1 (48 colunas)', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (52, '2018-11-19 12:51:26.289', 'GUILHOTINA', 'IMPRESSORA', 'S', 'Se o SAT-Flex deve enviar o comando para acionar a guilhotina ao final de cada impressão.\nDeixar como N quando a impressora já aciona a guilhotina automaticamente, para evitar corte duplo.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (6, '2017-03-06 11:49:44.086728', 'SERIE', 'SAT', '900015401-49', 'Número de série do equipamento fiscal SAT.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (21, '2017-03-06 11:49:44.086728', 'LOGOTIPO', 'REVENDA', 'C:\remarca\remarca.png', 'Caminho completo do arquivo de imagem representativa do logotipo da empresa revendedora do sistema.\nPara uma melhor qualidade, utilize uma imagem no formato PNG.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (25, '2017-03-06 11:49:44.086728', 'EMAIL', 'REVENDA', 'suporte@remarca-automacao.com.br', 'E-mails para contato da revendedora do sistema.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (17, '2017-03-06 11:49:44.086728', 'ATIVACAO', 'SAT', 'senha12345', 'Código de ativação do equipamento fiscal SAT.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (24, '2017-03-06 11:49:44.086728', 'TELEFONE', 'REVENDA', '(11) 2755-7911 Das 8:00h às 17:30h', 'Telefones para contato da revendedora do sistema.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (7, '2017-03-06 11:49:44.086728', 'IE', 'EMITENTE', '149392863111', 'Inscrição Estadual do emitente dos documentos fiscais.', 'S', 4)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (4, '2017-03-06 11:49:44.086728', 'CNPJ', 'DESENVOLVEDORA', '16716114000172', 'CNPJ da desenvolvedora da aplicação comercial.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (1, '2017-03-06 11:49:44.086728', 'CNPJ', 'EMITENTE', '08238299000129', 'CNPJ do emitente dos documentos fiscais.', 'S', 3)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (31, '2017-03-06 11:58:44.614151', 'INSTRUCAOSQL', 'ATUALIZACAO', '000073', 'Número da última instrução executada.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (29, '2017-03-06 11:49:44.086728', 'NOME', 'IMPRESSORA', 'COM4', 'Nome da impressora configurada no sistema operacional.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (37, '2018-11-19 11:51:02.208', 'NUMEROVIAS', 'IMPRESSORA', 'venda=1  cancelamento=1  orcamento=2', 'Número de vias a ser impressa para cada tipo de operação.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (15, '2017-03-06 11:49:44.086728', 'AMBIENTE', 'SAT', '2', 'Tipo de ambiente para transmissão dos documentos fiscais:\n1 - Produção\n2 - Homologação', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (40, '2018-11-19 11:51:02.358', 'DTVERIFICACAO', 'SISTEMA', 1, 'Data da última verificação do terminal no servidor SAT-Flex.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (18, '2017-03-06 11:49:44.086728', 'MODELO', 'SAT', '3', 'Modelo do equipamento fiscal SAT:\n1=nenhum, 2=sweda, 3=controlid', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (27, '2017-03-06 11:49:44.086728', 'MODELO', 'IMPRESSORA', '3', 'Modelo da impressora:\n1=nenhum, 2=sweda, 3=controlid, " +
                "4=epson', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (32, '2017-03-06 12:00:55.827362', 'EMAIL', 'CONTADOR', 'contador@contador.com.br', 'E-mail de contato do contador.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (39, '2018-11-19 11:51:02.358', 'STATUS', 'SISTEMA', '0000', 'Status do terminal, de acordo com o servidor SAT-Flex.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (53, '2018-11-19 11:51:02.358', 'DATABACKUP1', 'BACKUP', '', 'Data do backup.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (54, '2018-11-19 11:51:02.358', 'DATABACKUP2', 'BACKUP', '', 'Data do backup.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (55, '2018-11-19 11:51:02.358', 'DATABACKUP3', 'BACKUP', '', 'Data do backup.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (58, '2018-11-19 11:51:02.358', 'CHECABACKUP', 'BACKUP', '0', 'Status do backup, 1 backup feito e 0 backup não feito.', 'N', NULL)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "CREATE TABLE IF NOT EXISTS ademirmonstro(iddocumentopagamento INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "iddocumento integer NOT NULL,"+
                "idformapagamento integer NOT NULL,"+
                "totalpagamento numeric(12,2) NOT NULL,"+
                "FOREIGN KEY (iddocumento) REFERENCES documento (iddocumento),"+
                "FOREIGN KEY (idformapagamento) REFERENCES formapagamento (idformapagamento))");

    }
    public void addProduto(modelProdutos criar ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "descricao", criar.getDescricao() );
        values.put("idcategoria", criar.getIdcategoria());
        values.put( "preco", criar.getPreco() );
        values.put( "codigoean", criar.getCodigoean() );
        values.put( "precovariavel", criar.getPrecovariavel() );
        values.put( "status", criar.getStatus() );
        values.put( "csosn", criar.getCsosn() );
        values.put( "aliqicms", criar.getAliqicms() );
        values.put( "cstpis", criar.getCstpis() );
        values.put( "aliqpis", criar.getAliqpis() );
        values.put( "cstcofins", criar.getCstcofins() );
        values.put( "aliqcofins", criar.getAliqicofins() );
        values.put( "codcontribsocial", criar.getCodcontribsocial() );
        values.put( "cest", criar.getCest() );
        values.put( "cfop", criar.getCfop() );
        values.put( "idunidade", criar.getIdunidade() );
        values.put( "origem", criar.getOrigem() );
        values.put( "idncm", criar.getIdNcm() );
        db.insert( "produto" ,null, values  );
        db.close();
    }
    public void addalteraprod(modelProdutos altera ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "idproduto", altera.getIdproduto() );
        values.put( "descricao", altera.getDescricao() );
        values.put("idcategoria", altera.getIdcategoria());
        values.put( "preco", altera.getPreco() );
        values.put( "codigoean", altera.getCodigoean() );
        values.put( "precovariavel", altera.getPrecovariavel() );
        values.put( "status", altera.getStatus() );
        values.put( "csosn", altera.getCsosn() );
        values.put( "aliqicms", altera.getAliqicms() );
        values.put( "cstpis", altera.getCstpis() );
        values.put( "aliqpis", altera.getAliqpis() );
        values.put( "cstcofins", altera.getCstcofins() );
        values.put( "aliqcofins", altera.getAliqicofins() );
        values.put( "codcontribsocial", altera.getCodcontribsocial() );
        values.put( "cest", altera.getCest() );
        values.put( "cfop", altera.getCfop() );
        values.put( "idunidade", altera.getIdunidade() );
        values.put( "origem", altera.getOrigem() );
        values.put( "idncm", altera.getIdNcm() );

        teste = String.valueOf(altera.getIdproduto());
        String[] args = {teste};
        db.update( "produto", values, "idproduto=?", args);
        db.close();
    }
    public long addalteracateg(modelCategorias altera ){
        SQLiteDatabase db = this.getWritableDatabase();
        long retorno =0;
        ContentValues values = new ContentValues(  );
        try {
            values.put("idcategoria", altera.getIdcategoria());
            values.put("descricao", altera.getDescricao());
            values.put("cor", altera.getCor());
            values.put("origem", altera.getOrigem());
            values.put("cfop", altera.getCfop());
            values.put("csosn", altera.getCsosn());
            values.put("aliqicms", altera.getAliqicms());
            values.put("cstpis", altera.getCstpis());
            values.put("aliqpis", altera.getAliqpis());
            values.put("cstcofins", altera.getCstcofins());
            values.put("aliqcofins", altera.getAliqcofins());
            values.put("codcontribsocial", altera.getCodcontribsocial());
            values.put("cest", altera.getCest());
            values.put("idncm", altera.getIdNcm());

            //values.put("idncm", altera.getNcm());
            test2 = String.valueOf(altera.getIdcategoria());
            String[] args = {test2};
             retorno = db.update("categoria", values, "idcategoria=?", args);
            retorno = db.hashCode();
            db.close();
        }catch(SQLiteException e){
            e.hashCode();
            e.printStackTrace();
        }
        return retorno;
    }
    public void addCategoria(modelCategorias criar ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "descricao", criar.getDescricao() );
        values.put( "cor", criar.getCor());
        values.put("origem", criar.getOrigem());
        values.put("cfop", criar.getCfop());
        values.put("csosn", criar.getCsosn());
        values.put("aliqicms", criar.getAliqicms());
        values.put("cstpis",criar.getCstpis());
        values.put("aliqpis", criar.getAliqpis());
        values.put("cstcofins", criar.getCstcofins());
        values.put("aliqcofins", criar.getAliqcofins());
        values.put("codcontribsocial", criar.getCodcontribsocial());
        values.put("cest", criar.getCest());
        values.put("idncm",criar.getIdNcm());
        db.insert( "categoria" ,null, values  );
        db.close();

    }
    public int selecionarcateg ( String descrCat){
        SQLiteDatabase db = getReadableDatabase();
        int retorno = 0;
        Cursor cursor = db.query("categoria" , new String[]{"idcategoria"},"descricao" + " = ?", new String[]{descrCat}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
            retorno = cursor.getInt(0);
        }
        db.close();

        return  retorno;
    }
    public Cursor selectribcateg (String descrCat){
        SQLiteDatabase db = getReadableDatabase();
        Cursor retorno ;
        Cursor cursor = db.query("categoria" , new String[]{"aliqicms","cstpis","aliqpis","cstcofins","aliqcofins","codcontribsocial","csosn","origem","cfop","cest","idncm" },"descricao" + " = ?", new String[]{descrCat}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
                    }
        db.close();

        return  cursor;
    }
    public Cursor selecTribNcm (String codNcm){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("ncm" , new String[]{"aliqicms","cstpis","aliqpis","cstcofins","aliqcofins","codcontribsocial","csosn","origem","cfop","cest","idncm" },"codigoncm" + " = ?", new String[]{codNcm}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
        }
        db.close();

        return  cursor;
    }
    public Cursor selecItensOrcamento (String idDocProd){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("documentoproduto" , new String[]{"idproduto","quantidade","preco","descricao","descontounitario","totaldesconto","acrescimounitario","totalacrescimo","cfop","csosn","cstcofins","cstpis","origem","codigoncm" },"iddocumento" + " = ?", new String[]{idDocProd}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
        }
        db.close();

        return  cursor;
    }
    public int selecionarUnidade ( String descUnidade){
        SQLiteDatabase db = getReadableDatabase();
        int retorno = 0;
        Cursor cursor = db.query("unidade" , new String[]{"idunidade"},"descricao" + " = ?", new String[]{descUnidade}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
            retorno = cursor.getInt(0);
        }
        db.close();

        return  retorno;
    }
    public int selecionarNCM ( String codNcm){
        SQLiteDatabase db = getReadableDatabase();
        int retorno = 0;
        Cursor cursor = db.query("ncm" , new String[]{"idncm"},"codigoncm" + " = ?", new String[]{codNcm}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
            retorno = cursor.getInt(0);
        }
        db.close();

        return  retorno;
    }
    public String selecionarCodNCM ( String idNcm){
        SQLiteDatabase db = getReadableDatabase();
        String retorno = "";
        Cursor cursor = db.query("ncm" , new String[]{"codigoncm"},"idncm" + " = ?", new String[]{idNcm}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();
            retorno = cursor.getString(0);
        }
        db.close();

        return  retorno;
    }
    public Cursor selecValorParam ( String idParam){
        SQLiteDatabase db = getReadableDatabase();
        String retorno = "";
        Cursor cursor = db.query("parametro" , new String[]{"valor","observacao"},"idparametro" + " = ?", new String[]{idParam}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();

        }
        db.close();

        return  cursor;
    }
    public Cursor selecParametros ( ){
        SQLiteDatabase db = getReadableDatabase();
        String retorno = "";
        Cursor cursor = db.query("parametro" , new String[]{"idparametro","valor"},null,null, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();

        }
        db.close();

        return  cursor;
    }
    public Cursor selecValorSuporte ( ){
        SQLiteDatabase db = getReadableDatabase();
        String retorno = "";
        Cursor cursor = db.query("parametro" , new String[]{"valor"},"grupo" + " = ?", new String[]{"REVENDA"}, null   ,null,null,null);

        if (cursor != null){

            cursor.moveToFirst();

        }
        db.close();

        return  cursor;
    }
    public int selecionariddocumento ( ){
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder select = new StringBuilder();
        select.append("SELECT *FROM documento WHERE iddocumento = (SELECT MAX(iddocumento) FROM documento)");
        int retorno = 0;

        Cursor dados = db.rawQuery(select.toString(), null);

        if (dados.getCount() != 0){

dados.moveToNext();
            retorno = dados.getInt(dados.getColumnIndex( "iddocumento" ));
        }
        db.close();

        return  retorno;
    }
    public Cursor selecionarCadProd ( ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT produto.descricao, produto.codigoean, produto.status, produto.preco, categoria.descricao, produto.idproduto,strftime('%d/%m/%Y %H:%M',produto.dthrcriacao) , produto.csosn,produto.aliqicms,produto.cstpis,produto.aliqpis,produto.cstcofins,produto.aliqcofins,produto.codcontribsocial,produto.cest,produto.cfop, produto.idcategoria, ncm.codigoncm, produto.precovariavel, produto.idunidade, produto.origem FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade ORDER BY produto.descricao ASC";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
         db.close();

        return cursor;
    }
    public Cursor selecionarCadProd (String filtro ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT produto.descricao, produto.codigoean, produto.status, produto.preco, categoria.descricao, produto.idproduto,produto.dthrcriacao, produto.csosn, produto.aliqicms,produto.cstpis,produto.aliqpis,produto.cstcofins,produto.aliqcofins,produto.codcontribsocial,produto.cest, produto.cfop, produto.idcategoria, ncm.codigoncm, produto.precovariavel, produto.idunidade, produto.origem  FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.descricao LIKE '%" + filtro + "%' ";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
         db.close();

        return cursor;
    }
    public Cursor selecionarCadProdEan (String filtro ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT produto.descricao, produto.codigoean, produto.status, produto.preco, categoria.descricao, produto.idproduto,produto.dthrcriacao, produto.csosn, produto.aliqicms,produto.cstpis,produto.aliqpis,produto.cstcofins,produto.aliqcofins,produto.codcontribsocial,produto.cest, produto.cfop, produto.idcategoria, ncm.codigoncm, produto.precovariavel, produto.idunidade, produto.origem  FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.codigoean LIKE '%" + filtro + "%' ";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public Cursor selecionarCadCateg ( ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT categoria.descricao, ncm.codigoncm, COUNT(produto.idproduto), categoria.cor, categoria.idcategoria, strftime('%d/%m/%Y %H:%M',categoria.dthrcriacao) ,  categoria.origem, categoria.cfop, categoria.csosn, categoria.aliqicms, categoria.cstpis, categoria.aliqpis, categoria.cstcofins, categoria.aliqcofins, categoria.codcontribsocial, categoria.cest FROM categoria  " +
                "LEFT JOIN produto  ON produto.idcategoria  = categoria.idcategoria LEFT JOIN ncm  ON ncm.idncm  = categoria.idncm GROUP BY categoria.descricao ORDER BY categoria.descricao ASC ";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public Cursor selecionarCadCateg(String filtro ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT categoria.descricao, ncm.codigoncm, COUNT(produto.idproduto), categoria.cor, categoria.idcategoria, categoria.dthrcriacao,  categoria.origem, categoria.cfop, categoria.csosn, categoria.aliqicms, categoria.cstpis, categoria.aliqpis, categoria.cstcofins, categoria.aliqcofins, categoria.codcontribsocial, categoria.cest FROM categoria " +
                "LEFT JOIN produto  ON produto.idcategoria  = categoria.idcategoria LEFT JOIN ncm  ON ncm.idncm  = categoria.idncm WHERE categoria.descricao LIKE '%" + filtro + "%'";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public Cursor selecionarVendCateg( ) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("categoria", new String[]{"idcategoria", "descricao", "cor"}, null, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public Cursor selecionarVendProd(int idcateg ) {
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "SELECT produto.idproduto, produto.descricao, categoria.cor, produto.preco, ncm.codigoncm, produto.origem, produto.csosn, " +
                "produto.aliqicms, produto.cstpis, produto.aliqpis, produto.cstcofins, produto.aliqcofins, produto.codcontribsocial, produto.cest, produto.cfop, " +
                " produto.precovariavel, produto.idunidade FROM produto  " +
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.status = 'A'and produto.idcategoria =  "+idcateg+"  ORDER BY produto.descricao ASC";
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public Cursor selecionariddocCanc ( ) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder select = new StringBuilder(  );
        select.append("SELECT * FROM documento where operacao = 'CU' ");
        Cursor cursor = db.rawQuery(select.toString(), null);

        if (cursor != null) {

            cursor.moveToFirst();

        }
        return cursor;
    }
    public Cursor selecDocCancbyCoo (int coo ) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder select = new StringBuilder(  );
        select.append("SELECT * FROM documento where operacao = 'CU' and numero = "+coo+"");
        Cursor cursor = db.rawQuery(select.toString(), null);

        if (cursor != null) {

            cursor.moveToFirst();

        }
        db.close();

        return cursor;
    }
    public void alterdocCancela(String [] alterarDocCanc ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("chavecanc", alterarDocCanc[1]);
        values.put("status", "C");
        values.put("xmlcanc", alterarDocCanc[2]);
        values.put("dthrcancelamento",alterarDocCanc[3]);
        String[] args = {alterarDocCanc[0]};
        db.update( "documento", values, "iddocumento=?", args);
        db.close();
    }
    public void addalterparametro(modelParametros alterarParam ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", alterarParam.getValor() );

        teste = String.valueOf(alterarParam.getIdparametro());
        String[] args = {teste};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void adddocumento(modelDocumento orc ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "cpfcnpj",orc.getCpfcnpj() );
        values.put( "nomeorcamento",orc.getNomecliente());
        values.put( "operacao",orc.getOperacao() );
        values.put( "numeroprodutos", orc.getNumerodeprodutos() );
        values.put( "totalquantidade",orc.getTotalquantidade());
        values.put( "totaldocumento",orc.getTotaldocumento() );
        values.put ( "totaltroco",orc.getTotaltroco() );
        values.put ("totaldesconto",orc.getTotaldesconto());
        values.put ("totalacrescimo",orc.getTotalacrescimo());
        values.put ("xml",orc.getXml());
        values.put ("numero",orc.getNumero());
        values.put ("chave",orc.getChaveCfe());
        values.put  ("dthrcriacao",orc.getDthrcriacao());



        db.insert( "documento" ,null, values  );
        db.close();
    }
    public void adddocpagamento(modelDocumentoPagamento docpag ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "iddocumento", docpag.getIddocumento() );
        values.put( "idformapagamento", docpag.getIdformapagamento() );
        values.put( "totalpagamento", docpag.getTotalpagamento() );
        values.put( "dthrcriacao", docpag.getDthrcriacao() );
        db.insert( "documentopagamento" ,null, values  );
        db.close();

    }
    public void adddocumentoproduto(modelDocumentoProduto docProd ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "iddocumento",docProd.getIddocumento() );
        values.put( "sequencial",docProd.getSequencial() );
        values.put( "idproduto",docProd.getIddoproduto() );
        values.put( "descricao",docProd.getDescricao() );
        values.put( "preco",docProd.getPreco() );
        values.put( "quantidade",docProd.getQuantidade() );
        values.put( "descontounitario",docProd.getDescontounitario() );
        values.put( "totaldesconto",docProd.getTotaldesconto() );
        values.put( "totalproduto",docProd.getTotalprodutocdesc() );
        values.put( "cfop",docProd.getCfop() );
        values.put( "csosn",docProd.getCsosn() );
        values.put( "cstcofins",docProd.getCstcofins() );
        values.put( "cstpis",docProd.getCstpis() );
        values.put( "origem",docProd.getOrigem() );
        values.put( "codigoncm",docProd.getCodigoNcm() );
        values.put( "aliqicms",docProd.getAliqicms() );
        values.put( "totalproduto",docProd.getTotalprodutocdesc() );
        values.put ("totalacrescimo",docProd.getTotalacrescimo());
        values.put ("acrescimounitario",docProd.getAcrescimounitario());
        values.put( "dthrcriacao", docProd.getDthrcriacao() );

         db.insert( "documentoproduto" ,null, values  );
        db.close();
    }
    public void adddtVerificacao(String dtVerificar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", dtVerificar);
        String idparametro =String.valueOf(40);

        String[] args = {"40"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();

    }
    public void addStatus(String alterarStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", alterarStatus);

        String[] args = {"39"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void dataBackup1(String dataBackup1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", dataBackup1);

        String[] args = {"53"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void dataBackup2(String dataBackup2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", dataBackup2);

        String[] args = {"54"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void dataBackup3(String dataBackup3){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", dataBackup3);

        String[] args = {"55"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void checarBackup(String checarBackup){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", checarBackup);

        String[] args = {"58"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }
    public void inserirNcm(String ncm){
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL(ncm);
    }

    public void addVersao(int codVersao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", codVersao);
        String idparametro =String.valueOf(39);

        String[] args = {"47"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }

    public void addSuporte(int numSat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put("valor", numSat);
        String[] args = {"18"};
        db.update( "parametro", values, "idparametro=?", args);
        db.close();
    }

}
