package com.example.ademirestudo.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import model.modelDocumento;
import model.modelDocumentoPagamento;
import model.modelDocumentoProduto;
import model.modelProdutos;
import model.modelCategorias;
import model.modelParametros;

public class DadosOpenHelper extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 56;
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
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (2, '2017-03-16 16:45:53.942203', '0101.21.00', 'CAVALOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (3, '2017-03-16 16:45:53.942203', '0101.29.00', 'OUTROS CAVALOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4, '2017-03-16 16:45:53.942203', '0101.30.00', 'ASININOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (5, '2017-03-16 16:45:53.942203', '0101.90.00', 'OUTROS MUARES VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (6, '2017-03-16 16:45:53.942203', '0102.21.10', 'BOVINOS DOMÉST.REPROD.D/RAÇA PURA,PRENHES OU C/CRIA AO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (7, '2017-03-16 16:45:53.942203', '0102.21.90', 'OUTROS BOVINOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (8, '2017-03-16 16:45:53.942203', '0102.29.11', 'OUTROS BOVINOS P/REPRODUÇÃO, PRENHES OU C/CRIA AO PÉ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (9, '2017-03-16 16:45:53.942203', '0102.29.19', 'OUTROS BOVINOS DOMÉSTICOS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (10, '2017-03-16 16:45:53.942203', '0102.29.90', 'OUTROS BOVINOS DOMÉSTICOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (11, '2017-03-16 16:45:53.942203', '0102.31.10', 'BÚFALOS REPROD.DE RAÇA PURA, PRENHES OU C/CRIA AO PÉ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (12, '2017-03-16 16:45:53.942203', '0102.31.90', 'OUTROS BÚFALOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (13, '2017-03-16 16:45:53.942203', '0102.39.11', 'OUTROS BÚFALOS P/REPRODUÇÃO, PRENHES OU C/CRIA AO PÉ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (14, '2017-03-16 16:45:53.942203', '0102.39.19', 'OUTROS BÚFALOS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (15, '2017-03-16 16:45:53.942203', '0102.39.90', 'OUTROS BÚFALOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (16, '2017-03-16 16:45:53.942203', '0102.90.00', 'OUTROS ANIMAIS VIVOS DA ESPÉCIE BOVINA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (17, '2017-03-16 16:45:53.942203', '0103.10.00', 'REPRODUTORES DA ESPÉCIE SUÍNA DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (18, '2017-03-16 16:45:53.942203', '0103.91.00', 'OUTROS SUÍNOS REPROD.PESO INFERIOR 50 KG', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (19, '2017-03-16 16:45:53.942203', '0103.92.00', 'OUTS.SUÍNOS REPROD.PESO IGUAL OU SUP 50 KG', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (20, '2017-03-16 16:45:53.942203', '0104.10.11', 'OVINOS REP.RAÇA PURA,PRENHE OU C/CRIA AO PÉ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (21, '2017-03-16 16:45:53.942203', '0104.10.19', 'OUTROS OVINOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (22, '2017-03-16 16:45:53.942203', '0104.10.90', 'OUTROS ANIMAIS DA ESPÉCIES OVINAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (23, '2017-03-16 16:45:53.942203', '0104.20.10', 'CAPRINOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (24, '2017-03-16 16:45:53.942203', '0104.20.90', 'OUTS.CAPRINOS REPRODUTORES DE RAÇA PURA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (25, '2017-03-16 16:45:53.942203', '0105.11.10', 'GALO E GALINHA REP.RAÇA PUR/ HIBRI.Ñ SUP.185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (26, '2017-03-16 16:45:53.942203', '0105.11.90', 'OUTROS GALOS E GALINHAS NÃO SUPERIOR A 185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (27, '2017-03-16 16:45:53.942203', '0105.12.00', 'PERUAS E PERUS, PESO NÃO SUPERIOR A 185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (28, '2017-03-16 16:45:53.942203', '0105.13.00', 'PATOS DOMÉSTICOS, VIVOS DE PESO INFERIOR A 185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (29, '2017-03-16 16:45:53.942203', '0105.14.00', 'GANSOS DOMÉSTICOS, VIVOS DE PESO INFERIOR A 185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (30, '2017-03-16 16:45:53.942203', '0105.15.00', 'GALINHAS D''ANGOLA (PINTADAS) DOMEST.VIVAS D/PESO INF.A', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (31, '2017-03-16 16:45:53.942203', '0105.94.00', 'GALOS E GALINHAS DE PESO Ñ SUPERIOR A 185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (32, '2017-03-16 16:45:53.942203', '0105.99.00', 'OUTRAS AVES DAS ESP.DOMÉSTICAS,PESO >185G', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (33, '2017-03-16 16:45:53.942203', '0106.11.00', 'MAMÍFEROS PRIMATAS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (34, '2017-03-16 16:45:53.942203', '0106.12.00', 'BALEIAS,GOLF.,BOTOS;PEIXES-BOIS,DUDONG;OTÁRIAS,FOCAS,ET', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (35, '2017-03-16 16:45:53.942203', '0106.13.00', 'CAMELOS E OUTROS CAMELÍDIOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (36, '2017-03-16 16:45:53.942203', '0106.14.00', 'COELHOS E LEBRES VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (37, '2017-03-16 16:45:53.942203', '0106.19.00', 'OUTROS MAMÍFEROS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (38, '2017-03-16 16:45:53.942203', '0106.20.00', 'RÉPTEIS,SERPENTES E TARTAR.MARINHAS,VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (39, '2017-03-16 16:45:53.942203', '0106.31.00', 'AVES DE RAPINA VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (40, '2017-03-16 16:45:53.942203', '0106.32.00', 'PSITACIFORMES(PAP.PER.ARARAS,CACATUAS)VIVO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (41, '2017-03-16 16:45:53.942203', '0106.33.10', 'AVESTRUZES (STRUTHIO CAMELUS) PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (42, '2017-03-16 16:45:53.942203', '0106.33.90', 'OUTROS AVESTRUZES VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (43, '2017-03-16 16:45:53.942203', '0106.39.00', 'OUTRAS AVES VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (44, '2017-03-16 16:45:53.942203', '0106.41.00', 'ABELHAS VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (45, '2017-03-16 16:45:53.942203', '0106.49.00', 'OUTROS INSETOS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (46, '2017-03-16 16:45:53.942203', '0106.90.00', 'OUTROS ANIMAIS VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (47, '2017-03-16 16:45:53.942203', '0201.10.00', 'CARCAÇAS E MEIAS-CARCS. DE BOV., FRESC.REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (48, '2017-03-16 16:45:53.942203', '0201.20.10', 'QUARTOS DIANT. BOV. Ñ DESOSS. FRESC.OU REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (49, '2017-03-16 16:45:53.942203', '0201.20.20', 'QUARTOS TRAZ.BOV.Ñ DESOSS.FRESC.OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (50, '2017-03-16 16:45:53.942203', '0201.20.90', 'OUTS CARNES BOV.Ñ DESOSS.FRESC OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (51, '2017-03-16 16:45:53.942203', '0201.30.00', 'OUTS CARNES BOV. DESOSS.FRESC OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (52, '2017-03-16 16:45:53.942203', '0202.10.00', 'CARCAÇAS E MEIAS-CARCAÇAS DE BOVINOS,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (53, '2017-03-16 16:45:53.942203', '0202.20.10', 'QUART.DIANTEIRO DE BOVINO NÃO DESOSS., CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (54, '2017-03-16 16:45:53.942203', '0202.20.20', 'QUART.TRASEIRO DE BOVINO NÃO DESOSS., CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (55, '2017-03-16 16:45:53.942203', '0202.20.90', 'OUTRAS CARNES BOV. NÃO DESOSS.,CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (56, '2017-03-16 16:45:53.942203', '0202.30.00', 'CARNES DE BOVINO DESOSSADAS, CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (57, '2017-03-16 16:45:53.942203', '0203.11.00', 'CARCS E MEIAS-CARCS. DE SUÍNOS, FRESC., REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (58, '2017-03-16 16:45:53.942203', '0203.12.00', 'PERNA E PEDAÇO Ñ DESOS. SUÍNOS, FRESC.,REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (59, '2017-03-16 16:45:53.942203', '0203.19.00', 'OUTRAS CARNES DE SUÍNOS, FRESCAS OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (60, '2017-03-16 16:45:53.942203', '0203.21.00', 'CARCS. E MEIAS-CARCS. DE SUÍNOS, CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (61, '2017-03-16 16:45:53.942203', '0203.22.00', 'PERNA E PEDAÇO Ñ DESOS. SUÍNOS, CONGELADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (62, '2017-03-16 16:45:53.942203', '0203.29.00', 'OUTRAS CARNES DE SUÍNO,CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (63, '2017-03-16 16:45:53.942203', '0204.10.00', 'CARCS.E MEIAS-CARCS D/CORDEIRO,FRESC.,REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (64, '2017-03-16 16:45:53.942203', '0204.21.00', 'CARCS.E MEIAS-CARCS.DE OVINOS,FRESC.REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (65, '2017-03-16 16:45:53.942203', '0204.22.00', 'OUTRAS PEÇAS Ñ DESOSS.D/OVINOS,FRESC.REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (66, '2017-03-16 16:45:53.942203', '0204.23.00', 'PÇS DESOSSADAS D/OVINOS,FRESCAS OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (67, '2017-03-16 16:45:53.942203', '0204.30.00', 'CARCS. E MEIAS-CARCS. DE CORDEIRO,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (68, '2017-03-16 16:45:53.942203', '0204.41.00', 'CARCS. E MEIAS-CARCS. OUTS.OVINOS,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (69, '2017-03-16 16:45:53.942203', '0204.42.00', 'OUTS.PÇS NÃO DESOSS.DE OVINOS,CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (70, '2017-03-16 16:45:53.942203', '0204.43.00', 'PEÇAS DESOSSADAS DE OVINOS,CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (71, '2017-03-16 16:45:53.942203', '0204.50.00', 'CARNES DE CAPRINOS,FRESC.,REFRIG.OU CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (72, '2017-03-16 16:45:53.942203', '0205.00.00', 'CARNE CAVALO,ASININA,MUAR,FRESC.REF.,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (73, '2017-03-16 16:45:53.942203', '0206.10.00', 'MIUDEZAS DE BOVINOS,FRESCAS/REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (74, '2017-03-16 16:45:53.942203', '0206.21.00', 'LÍNGUAS DE BOVINOS CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (75, '2017-03-16 16:45:53.942203', '0206.22.00', 'FÍGADOS DE BOVINOS CONGELADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (76, '2017-03-16 16:45:53.942203', '0206.29.10', 'RABOS DE BOVINOS CONGELADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (77, '2017-03-16 16:45:53.942203', '0206.29.90', 'OUTRAS MIUDEZAS COMESTÍVEIS., D/BOVINO,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (78, '2017-03-16 16:45:53.942203', '0206.30.00', 'MIUDEZAS DA ESPÉCIE SUÍNA FRESCAS OU REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (79, '2017-03-16 16:45:53.942203', '0206.41.00', 'FÍGADOS DE SUÍNOS, CONGELADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (80, '2017-03-16 16:45:53.942203', '0206.49.00', 'OUTRAS MIUDEZAS DE SUÍNOS, CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (81, '2017-03-16 16:45:53.942203', '0206.80.00', 'OUTRAS MIUDEZAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (82, '2017-03-16 16:45:53.942203', '0206.90.00', 'OUTRAS MIUDEZAS CONGELADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (83, '2017-03-16 16:45:53.942203', '0207.11.00', 'CARNES GALOS/GALINHAS,Ñ CORT.,FRESCAS/REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (84, '2017-03-16 16:45:53.942203', '0207.12.00', 'CARNES GALOS/GALINHAS,Ñ CORT.,CONGELADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (85, '2017-03-16 16:45:53.942203', '0207.13.00', 'PEDAÇOS/MIUDEZ.,D/GALOS/GALINHAS,FRESC,REF.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (86, '2017-03-16 16:45:53.942203', '0207.14.00', 'PEDAÇOS E MIUDEZ.DE GALOS E GALINHAS,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (87, '2017-03-16 16:45:53.942203', '0207.24.00', 'CARNES DE PERUAS/PERUS, Ñ CORT.,FRES/REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (88, '2017-03-16 16:45:53.942203', '0207.25.00', 'CARNES DE PERUS/AS Ñ CORT.PEDAÇOS, CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (89, '2017-03-16 16:45:53.942203', '0207.26.00', 'CARNES DE PERUAS/US,MIUDEZ.,FRESCOS/REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (90, '2017-03-16 16:45:53.942203', '0207.27.00', 'CARNES PEDAÇOS E MIUDEZ.DE PERUS/AS,CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (91, '2017-03-16 16:45:53.942203', '0207.41.00', 'CARNES E MIUDEZAS COMEST.FRESCAS/REFRIG.Ñ CORT.DE PATOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (92, '2017-03-16 16:45:53.942203', '0207.42.00', 'CARNES E MIUDEZAS COMEST.CONG. Ñ CORT.DE PATOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (93, '2017-03-16 16:45:53.942203', '0207.43.00', 'FÍGADOS GORDOS (FOIES GRAS), FRESCOS/REFRIG. DE PATOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (94, '2017-03-16 16:45:53.942203', '0207.44.00', 'OUTRAS CARNES E MIUDEZAS FRESCAS/REFRIG. DE PATOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (95, '2017-03-16 16:45:53.942203', '0207.45.00', 'OUTRAS CARNES E MIUDEZAS CONGELADAS DE PATOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (96, '2017-03-16 16:45:53.942203', '0207.51.00', 'CARNES E MIUDEZAS COMEST.FRESCAS/REFRIG.Ñ CORT.DE GANSO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (97, '2017-03-16 16:45:53.942203', '0207.52.00', 'CARNES E MIUDEZAS COMEST.CONG. Ñ CORT.DE GANSOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (98, '2017-03-16 16:45:53.942203', '0207.53.00', 'FÍGADOS GORDOS (FOIES GRAS), FRESCOS/REFRIG. DE GANSOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (99, '2017-03-16 16:45:53.942203', '0207.54.00', 'OUTRAS CARNES E MIUDEZAS FRESCAS/REFRIG. DE GANSOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (100, '2017-03-16 16:45:53.942203', '0207.55.00', 'OUTRAS CARNES E MIUDEZAS CONGELADAS DE GANSOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (101, '2017-03-16 16:45:53.942203', '0207.60.00', 'CARNES,MIUD.COMEST.FRESC.REFR.CONG.D/GALINAS-D''ANGOLA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (102, '2017-03-16 16:45:53.942203', '0208.10.00', 'CARNES,MIUDEZ.D/COELHO,LEBRE,FR/REFR.CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (103, '2017-03-16 16:45:53.942203', '0208.30.00', 'CARNES,MIUD.COM.D/PRIMATAS FRES.REFR.CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (104, '2017-03-16 16:45:53.942203', '0208.40.00', 'CARNES,MIUD.COMEST.FRESC.REFR.CONG.D/BALEIAS,GOLF.BOT.,', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (105, '2017-03-16 16:45:53.942203', '0208.50.00', 'CARN.MIUD.D/RÉPTS,SERP.TART.MAR.FR.REF.CONG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (106, '2017-03-16 16:45:53.942203', '0208.60.00', 'CARNES,MIUD.COMEST.FRESC.REFR.CONG.D/CAMELOS E OUTROS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (107, '2017-03-16 16:45:53.942203', '0208.90.00', 'OUTRAS CARNES E MIUD.COMEST.FRESCO REFRIG./CONGELADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (108, '2017-03-16 16:45:53.942203', '0209.10.11', 'TOUCINHO DE PORCO FRESCO, REFRIGERADO/CONGELADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (109, '2017-03-16 16:45:53.942203', '0209.10.19', 'TOUCINHO DE PORCO SALGADO,EM SALMOURA,SECO OU DEFUMADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (110, '2017-03-16 16:45:53.942203', '0209.10.21', 'GORDURA DE PORCO FRESCA REFRIGERADA OU CONGELADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (111, '2017-03-16 16:45:53.942203', '0209.10.29', 'GORDURA DE PORCO SALGADA OU EM SALMOURA, SECA/DEFUMADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (112, '2017-03-16 16:45:53.942203', '0209.90.00', 'OUTS.GORD.DE AVES FRESC.REFR.CONG.SALG.SALM.SECOS/DEFUM', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (113, '2017-03-16 16:45:53.942203', '0210.11.00', 'PERNAS,PÁS,ETC.Ñ DESOSS.SUÍNOS,SALG.SECAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (114, '2017-03-16 16:45:53.942203', '0210.12.00', 'BARRIGAS E PEITOS Ñ DESOSS.SUÍNOS,SALG.SECA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (115, '2017-03-16 16:45:53.942203', '0210.19.00', 'OUTS.CARNES SUÍNAS,SALG.SECAS OU DEFUMS.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (116, '2017-03-16 16:45:53.942203', '0210.20.00', 'CARNES BOVINAS,SALGADAS,SECAS OU DEFUMS.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (117, '2017-03-16 16:45:53.942203', '0210.91.00', 'CARN.MIUD,D/PRIM.SAL.SEC.DEF.FAR.D/CARN/MIUD.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (118, '2017-03-16 16:45:53.942203', '0210.92.00', 'CARNES E MIUD.;FARINH.E PÓS COMEST.D/BALEIAS,GOLF.BOTOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (119, '2017-03-16 16:45:53.942203', '0210.93.00', 'CARN.MIUD.D/RÉPTEIS,SERP.TAR,DEF.F/CAR.MIUD.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (120, '2017-03-16 16:45:53.942203', '0210.99.00', 'OUTS.CARNES E MIUD.COMEST.FARINH.E PÓS,SALG.OU EM SALM.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (121, '2017-03-16 16:45:53.942203', '0301.11.10', 'ARUANÃ (OSTEOGLOSSUM BICIRRHOSUM)', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (122, '2017-03-16 16:45:53.942203', '0301.11.90', 'OUTROS PEIXES VIVOS ORNAMENTAIS DE ÁGUA DOCE', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (123, '2017-03-16 16:45:53.942203', '0301.19.00', 'PEIXES VIVOS ORNAMENTAIS DE ÁGUA SALGADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (124, '2017-03-16 16:45:53.942203', '0301.91.10', 'TRUTAS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (125, '2017-03-16 16:45:53.942203', '0301.91.90', 'OUTRAS TRUTAS (SALMO TRUTTA ETC.)VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (126, '2017-03-16 16:45:53.942203', '0301.92.10', 'ENGUIAS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (127, '2017-03-16 16:45:53.942203', '0301.92.90', 'OUTRAS ENGUIAS (ANGUILLA SPP.) VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (128, '2017-03-16 16:45:53.942203', '0301.93.10', 'CARPAS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (129, '2017-03-16 16:45:53.942203', '0301.93.90', 'OUTRAS CARPAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (130, '2017-03-16 16:45:53.942203', '0301.94.10', 'ATUNS AZUIS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (131, '2017-03-16 16:45:53.942203', '0301.94.90', 'OUTROS ATUNS AZUIS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (132, '2017-03-16 16:45:53.942203', '0301.95.10', 'ATUNS-DO-SUL (THUNNUS MACCOYII) P/REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (133, '2017-03-16 16:45:53.942203', '0301.95.90', 'OUTS. ATUNS-DO-SUL (THUNNUS MACCOYII) P/REPR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (134, '2017-03-16 16:45:53.942203', '0301.99.11', 'TILÁPIAS PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (135, '2017-03-16 16:45:53.942203', '0301.99.12', 'ESTURJÕES PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (136, '2017-03-16 16:45:53.942203', '0301.99.19', 'OUTROS PEIXES PARA REPRODUÇÃO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (137, '2017-03-16 16:45:53.942203', '0301.99.91', 'OUTRAS TILÁPIAS VIVAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (138, '2017-03-16 16:45:53.942203', '0301.99.92', 'OUTROS ESTURJÕES VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (139, '2017-03-16 16:45:53.942203', '0301.99.99', 'OUTROS PEIXES VIVOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (140, '2017-03-16 16:45:53.942203', '0302.11.00', 'TRUTAS (SALMO TRUTTA,ETC.) FRESCAS OU REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (141, '2017-03-16 16:45:53.942203', '0302.13.00', 'SALMÕES-DO PACÍFICO FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (142, '2017-03-16 16:45:53.942203', '0302.14.00', 'SALMÃO-DO-ATLÂNTICO E SALMÃO-DO-DANÚBIO FRESC./REFRIGER', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (143, '2017-03-16 16:45:53.942203', '0302.19.00', 'OUTS.SALMONÍDEOS,FRESCOS OU REFR.,EXC.FILÉS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (144, '2017-03-16 16:45:53.942203', '0302.21.00', 'LINGUADOS-GIGANTES, FRESC.OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (145, '2017-03-16 16:45:53.942203', '0302.22.00', 'SOLHAS OU PATRUÇAS, FRESCAS OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (146, '2017-03-16 16:45:53.942203', '0302.23.00', 'LINGUADOS (SOLEA SPP.), FRESCOS OU REFRIG.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (147, '2017-03-16 16:45:53.942203', '0302.24.00', 'PREGADO FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (148, '2017-03-16 16:45:53.942203', '0302.29.00', 'OUTROS PEIXES CHATOS, FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (149, '2017-03-16 16:45:53.942203', '0302.31.00', 'ATUNS-BRANCOS OU GERMÕES FRESCOS/REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (150, '2017-03-16 16:45:53.942203', '0302.32.00', 'ALBACORAS(THUNNUS ALBACARES), FRES.,REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (151, '2017-03-16 16:45:53.942203', '0302.33.00', 'BONITO-LISTR/VENTR RAIAD, FRES.,REFR.,EXC.FILÉS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (152, '2017-03-16 16:45:53.942203', '0302.34.00', 'ALBACORAS-BAND.(THUNN OBESUS)FRESC.REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (153, '2017-03-16 16:45:53.942203', '0302.35.00', 'ATUNS AZUIS, FRESCOS OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (154, '2017-03-16 16:45:53.942203', '0302.36.00', 'ATUNS DO SUL (THUNNUS MACCOYII) FRESCO/REFR.', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (155, '2017-03-16 16:45:53.942203', '0302.39.00', 'OUTROS ATUNS FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (156, '2017-03-16 16:45:53.942203', '0302.41.00', 'ARANQUES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (157, '2017-03-16 16:45:53.942203', '0302.42.10', 'ANCHOITA FRESCA OU REFRIGERADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (158, '2017-03-16 16:45:53.942203', '0302.42.90', 'OUTROS ARENQUES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (159, '2017-03-16 16:45:53.942203', '0302.43.00', 'SARDINHAS, ANCHOVETAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (160, '2017-03-16 16:45:53.942203', '0302.44.00', 'CAVALINHAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (161, '2017-03-16 16:45:53.942203', '0302.45.00', 'CHICHARROS FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (162, '2017-03-16 16:45:53.942203', '0302.46.00', 'BIJUPIRÁ FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (163, '2017-03-16 16:45:53.942203', '0302.47.00', 'ESPADARTE FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (164, '2017-03-16 16:45:53.942203', '0302.49.10', 'ARLINS, VELEIROS (ISTIOPHORIDAE)', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (165, '2017-03-16 16:45:53.942203', '0302.51.00', 'BACALHAU-DO-ATLÂNTICO; DA-GROELÂNDIA D/PACÍF.FRESC./REF', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (166, '2017-03-16 16:45:53.942203', '0302.52.00', 'HADDOCK OU LUBINA FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (167, '2017-03-16 16:45:53.942203', '0302.53.00', 'SAITHE (POLLACHIUS VIRENS) FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (168, '2017-03-16 16:45:53.942203', '0302.54.00', 'MERLUZAS E ABRÓTEAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (169, '2017-03-16 16:45:53.942203', '0302.55.00', 'MERLUZA-DO-ALASCA FRESCA OU REFRIGERADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (170, '2017-03-16 16:45:53.942203', '0302.56.00', 'VERDINHOS FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (171, '2017-03-16 16:45:53.942203', '0302.59.00', 'OUTROS PEIXES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (172, '2017-03-16 16:45:53.942203', '0302.71.00', 'TILÁPIAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (173, '2017-03-16 16:45:53.942203', '0302.72.10', 'BAGRE FRESCO OU REFRIGERADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (174, '2017-03-16 16:45:53.942203', '0302.72.90', 'OUTROS BAGRES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (175, '2017-03-16 16:45:53.942203', '0302.73.00', 'CARPAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (176, '2017-03-16 16:45:53.942203', '0302.74.00', 'ENGUIAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (177, '2017-03-16 16:45:53.942203', '0302.79.00', 'OUTROS PEIXES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (178, '2017-03-16 16:45:53.942203', '0302.81.00', 'CAÇÃO E OUTROS TUBARÕES FRESCOS OU REFIRGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (179, '2017-03-16 16:45:53.942203', '0302.82.00', 'RAIAS FRESCAS OU REFRIGERADAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (180, '2017-03-16 16:45:53.942203', '0302.83.10', 'MERLUZA NEGRA (DISSOSTICHUS ELEGINOIDES)', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (181, '2017-03-16 16:45:53.942203', '0302.83.20', 'MERLUZA ANTÁRTICA FRESCA OU REFRIGERADA', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (182, '2017-03-16 16:45:53.942203', '0302.84.00', 'ROBALOS FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (183, '2017-03-16 16:45:53.942203', '0302.85.00', 'PARGOS OU SARGOS (SPARIDAE) FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (184, '2017-03-16 16:45:53.942203', '0302.89.10', 'ANUS PURPUREUS) ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (185, '2017-03-16 16:45:53.942203', '0302.89.11', 'AGULHÕES FRESCOS OU REFRIGERADOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (337, '2017-03-16 16:45:53.942203', '0304.88.90', 'OUTROS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (843, '2017-03-16 16:45:53.942203', '1108.11.00', 'AMIDO DE TRIGO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (1155, '2017-03-16 16:45:53.942203', '2007.99.23', 'DE BANANA (MUSA SPP.)', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (2222, '2017-03-16 16:45:53.942203', '2910.90.30', 'ENDRIN', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (3124, '2017-03-16 16:45:53.942203', '2933.91.12', 'CAMAZEPAM', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (3936, '2017-03-16 16:45:53.942203', '3201.90.20', 'TANINOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4336, '2017-03-16 16:45:53.942203', '3808.99.95', 'OUTROS NEMATICIDAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4582, '2017-03-16 16:45:53.942203', '3907.20.39', 'OUTROS POLIETERPOLIÓIS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4723, '2017-03-16 16:45:53.942203', '3919.10.90', 'OUTRAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4830, '2017-03-16 16:46:12.347725', '4002.91.00', 'LÁTEX', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (4952, '2017-03-16 16:46:12.347725', '4104.19.90', 'COUROS/PELES,EQUIDEOS,UMIDOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (5101, '2017-03-16 16:46:12.347725', '4410.11.90', 'OUTROS PAINÉIS DE PARTÍCULAS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (5733, '2017-03-16 16:46:12.347725', '5502.00.20', 'CABOS DE RAIOM VISCOSE', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (5777, '2017-03-16 16:46:12.347725', '5510.11.12', 'DE MODAL', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (6657, '2017-03-16 16:46:12.347725', '7005.30.00', 'VIDRO ARMADO', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (7171, '2017-03-16 16:46:12.347725', '7405.00.00', 'LIGAS-MÃES DE COBRE', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (7397, '2017-03-16 16:46:12.347725', '8112.21.20', 'CROMO EM PÓ', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (8367, '2017-03-16 16:46:22.300052', '8466.20.10', 'PORTA-PEÇAS P/TORNOS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (8548, '2017-03-16 16:46:22.300052', '8479.82.10', 'MISTURADORES', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");
        db.execSQL("INSERT INTO ncm (idncm, dthrcriacao, codigoncm, descricao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop) VALUES (9590, '2017-03-16 17:24:49.983694', '9015.10.00', 'TELÊMETROS', '0', '102', 0, '49', 0, '49', 0, '01', NULL, '5.102')");


        db.execSQL("CREATE TABLE IF NOT EXISTS categoria (idcategoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao  varyingcharacter(100) NOT NULL UNIQUE," +
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


        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (1, '2019-08-09 14:39:18', 'REMARCA', 'N', 1, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#FF4500', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (7, '2018-02-09 15:05:48.178265', 'BEBIDAS', 'N', 114, 'S', '0', '400', 0, '49', 0, '49', 0, '01', '#D15C5C', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (8, '2018-02-09 15:05:48.178265', 'SUCOS', 'N', 123, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#4CADE0', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (9, '2018-02-09 15:05:48.178265', 'ÁGUA', 'N', 123, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#96E04C', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (10, '2018-02-09 15:05:48.178265', 'VITAMINAS', 'N', 124, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#F0C94A', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (11, '2018-02-09 15:05:48.178265', 'CAFÉ DA MANHÃ', 'N', 126, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#60E0DE', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (12, '2018-02-09 15:05:48.178265', 'SALGADOS', 'N', 123, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#DE50DB', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (13, '2018-02-09 15:05:48.178265', 'CIGARRO', 'N', 131, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#ADADAD', NULL, '5.102')");
        db.execSQL("INSERT INTO categoria (idcategoria, dthrcriacao, descricao, padrao, idncm, sinctributacao, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cor, cest, cfop) VALUES (14, '2018-02-09 15:05:48.178265', 'DOÇES', 'N', 122, 'S', '0', '102', 0, '49', 0, '49', 0, '01', '#E8F04D', NULL, '5.102')");





        db.execSQL("CREATE TABLE IF NOT EXISTS produto ( idproduto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "dthrcriacao DATE DEFAULT (datetime('now','localtime'))," +
                "descricao varyingcharacter(100) NOT NULL UNIQUE,"+
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

        db.execSQL("INSERT INTO produto (idproduto,descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (1,  'TMT-20', 1, 8, '', 'N', 'N', 'S', 1, '0', '900', 0, '99', 0, '99', 0, '01', NULL, '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (2, '2017-03-08 12:12:24.30624', 'GRAXAS', 1, 5, '12', 'S', 'N', 'N', 1, '0', '102', 0, '49', 0, '49', 0, '00', '28.056.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (3, '2017-03-08 12:12:58.334254', 'PALMILHAS', 1, 1.2, '1', 'S', 'N', 'N', 1, '0', '102', 0, '49', 0, '49', 0, '00', '28.059.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (4, '2017-03-08 13:09:21.159284', 'MEIAS', 1, 0, NULL, 'S', 'N', 'N', 5, '0', '102', 0, '49', 0, '49', 0, '00', '28.059.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (5, '2017-03-08 13:10:56.038526', 'PROTEÇÃO PARA OS PÉS', 1, 0, NULL, 'S', 'N', 'N', 4, '0', '102', 0, '49', 0, '49', 0, '00', '28.059.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (6, '2017-03-08 13:12:06.945821', 'ESCOVAS', 1, 0, NULL, 'S', 'N', 'N', 6, '0', '102', 0, '49', 0, '49', 0, '00', '28.057.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (7, '2017-03-08 14:30:58.234655', 'IMPERMEABILIZANTES', 1, 0, NULL, 'S', 'N', 'N', 7, '0', '102', 0, '49', 0, '49', 0, '00', '28.063.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (8, '2017-03-08 14:31:42.996265', 'LIMPEZA PARA COURO', 1, 0, NULL, 'S', 'N', 'N', 8, '0', '102', 0, '49', 0, '49', 0, '00', '28.063.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (9, '2017-03-08 14:32:24.713344', 'LIMPEZA TENIS', 1, 0, NULL, 'S', 'N', 'N', 9, '0', '102', 0, '49', 0, '49', 0, '00', '28.063.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (10, '2017-03-08 14:33:10.722404', 'CALÇADEIRAS', 1, 0, NULL, 'S', 'N', 'N', 10, '0', '102', 0, '49', 0, '49', 0, '00', '28.057.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (11, '2017-03-08 14:34:36.065177', 'LASSEADOR SAPATOS', 1, 0, NULL, 'S', 'N', 'N', 7, '0', '102', 0, '49', 0, '49', 0, '00', '28.063.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (12, '2017-03-08 14:35:21.272699', 'TINTAS', 1, 0, NULL, 'S', 'N', 'N', 11, '0', '102', 0, '49', 0, '49', 0, '00', '24.001.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (13, '2017-03-08 14:36:09.184387', 'FORMAS', 1, 0, NULL, 'S', 'N', 'N', 12, '0', '102', 0, '49', 0, '49', 0, '00', '28.057.00', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (46, '2018-02-09 15:05:48.178265', 'CERVEJA 600 ML', 7, 0, NULL, 'S', 'N', 'N', 128, '0', '500', 0, '49', 0, '49', 0, '01', '49', '5.405', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (50, '2018-02-09 15:05:48.178265', 'AGUA', 9, 2.5, '11', 'S', 'N', 'N', 1, '0', '50', 0, '49', 0, '49', 0, '01', '49', '5.405', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (52, '2018-02-09 15:05:48.178265', 'CAFÉ', 11, 7.0, NULL, 'N', 'S', 'N', 168, '0', '102', 0, '49', 0, '49', 0, '01', '49', '5.102', 'A', 1)");
        db.execSQL("INSERT INTO produto (idproduto, dthrcriacao, descricao, idcategoria, preco, codigoean, precovariavel, favorito, sinctributacao, idncm, origem, csosn, aliqicms, cstpis, aliqpis, cstcofins, aliqcofins, codcontribsocial, cest, cfop, status, idunidade) VALUES (53, '2018-02-09 15:05:48.178265', 'CAFÉ COM LEITE', 11, 4.0, NULL, 'N', 'S', 'N', 128, '0', '102', 0, '49', 0, '49', 0, '01', '49', '5.102', 'A', 1)");

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
                " pesoliquido numeric(12,3)," +
                " qtdevolume integer," +
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
                "cfop varyingcharacter(5) NOT NULL DEFAULT '99',"+    //DEFAULT '99'
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
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (23, '2017-03-06 11:49:44.086728', 'ENDERECO', 'REVENDA', 'Rua Pio XI, 576 - Lapa  São Paulo - SP                CEP: 05060-000', 'Endereço da revendedora do sistema.', 'S', NULL)");
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
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (46, '2018-11-19 11:51:11.85', 'HOST', 'BACKUP', 'websac.net', 'Endereço de IP do FTP de onde será efetuado backup do banco de dados.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (35, '2018-11-19 11:51:02.022', 'SERIE', 'NOTAFISCAL', '1', 'Serie utilizada para notas fiscais.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (42, '2018-11-19 11:51:03.822', 'BOTAO10PORCENTO', 'DIVERSOS', 'N', 'Habilita o botão de acesso rápido para incluir a taxa de 10% de serviço.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (36, '2018-11-19 11:51:02.133', 'MUNICIPIOIBGE', 'NOTAFISCAL', '355030', 'Código do município do emitente de acordo com a tabela IBGE.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (43, '2018-11-19 11:51:03.904', 'ATIVO', 'NOTAFISCAL', 'N', 'Habilita o módulo de nota fiscal.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (38, '2018-11-19 11:51:02.286', 'CAMINHOCERTIFICADO', 'NOTAFISCAL', '', 'Caminho completo ate o arquivo do certificado digital do emitente das notas fiscais.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (47, '2018-11-19 11:51:11.85', 'PORTA', 'BACKUP', '21', 'Porta do FTP de onde será efetuado backup do banco de dados.', 'S', NULL)");
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
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (40, '2018-11-19 11:51:02.358', 'DTVERIFICACAO', 'SISTEMA', '2019-01-23', 'Data da última verificação do terminal no servidor SAT-Flex.', 'N', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (18, '2017-03-06 11:49:44.086728', 'MODELO', 'SAT', 'controlid', 'Modelo do equipamento fiscal SAT:\nnenhum, sweda, controlid', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (27, '2017-03-06 11:49:44.086728', 'MODELO', 'IMPRESSORA', 'escpos', 'Modelo da impressora:\nnenhum, escpos', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (32, '2017-03-06 12:00:55.827362', 'EMAIL', 'CONTADOR', 'contador@contador.com.br', 'E-mail de contato do contador.', 'S', NULL)");
        db.execSQL("INSERT INTO parametro (idparametro, dthrcriacao, nome, grupo, valor, observacao, visivel, ordem) VALUES (39, '2018-11-19 11:51:02.358', 'STATUS', 'SISTEMA', '0000', 'Status do terminal, de acordo com o servidor SAT-Flex.', 'N', NULL)");





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


    /*
    Objetivo =Cadastrar novo produto
    parametro = objeto modelProduto
    Retorno = void
     */
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

    public void adddocpagamento(modelDocumentoPagamento docpag ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues(  );
        values.put( "iddocumento", docpag.getIddocumento() );
        values.put( "idformapagamento", docpag.getIdformapagamento() );
        values.put( "totalpagamento", docpag.getTotalpagamento() );
        db.insert( "documentopagamento" ,null, values  );
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
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade";
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
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.descricao LIKE '%" + filtro + "%'";
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
                "LEFT JOIN produto  ON produto.idcategoria  = categoria.idcategoria LEFT JOIN ncm  ON ncm.idncm  = categoria.idncm GROUP BY categoria.descricao ";
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
                "LEFT JOIN categoria  ON categoria.idcategoria  = produto.idcategoria LEFT JOIN ncm  ON ncm.idncm  = produto.idncm LEFT JOIN unidade  ON unidade.idunidade  = produto.idunidade WHERE produto.status = 'A' and produto.idcategoria = " + idcateg;
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
       // db.close();

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
     //   teste = String.valueOf(alterarDocCanc[0]);
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

         db.insert( "documentoproduto" ,null, values  );
        db.close();
    }


}
