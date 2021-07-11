package ipg.primeiro.projetofinalcovid

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import ipg.primeiro.projetofinalcovid.basedados.*
import ipg.primeiro.projetofinalcovid.classedastabelas.*


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {


    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())

    private  fun insereDistrito(tabela: TabelaDistritos, distrito: Distrito): Long {
        val id = tabela.insert(distrito.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getDistritoBaseDados(tabela: TabelaDistritos, id: Long): Distrito {
        val cursor = tabela.query(
            TabelaDistritos.TODAS_COLUNAS,
            "${TabelaDistritos.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Distrito.fromCursor(cursor)
    }

    private fun getPessoaBaseDados(tabela: TabelaPessoas, id: Long): Pessoa {
        val cursor = tabela.query(
            TabelaPessoas.TODAS_COLUNAS,
            "${TabelaPessoas.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pessoa.fromCursor(cursor)
    }

    private fun inserePesssoa(tabela: TabelaPessoas, pessoa: Pessoa): Long {
        val id = tabela.insert(pessoa.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getTesteBaseDados(tabela: TabelaTestes, id: Long): Teste {
        val cursor = tabela.query(
            TabelaTestes.TODAS_COLUNAS,
            "${TabelaTestes.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Teste.fromCursor(cursor)
    }

    private fun getNotificacaoBaseDados(tabela: TabelaNotificacao, id: Long): Notificacao {
        val cursor = tabela.query(
            TabelaNotificacao.TODAS_COLUNAS,
            "${TabelaNotificacao.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Notificacao.fromCursor(cursor)
    }

    private fun insereTeste(tabela: TabelaTestes, teste: Teste): Long {
        val id = tabela.insert(teste.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereNotificacao(tabela: TabelaNotificacao, notificacao: Notificacao): Long {
        val id = tabela.insert(notificacao.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private  fun insereAlerta(tabela: TabelaAlertas, alerta: Alerta): Long {
        val id = tabela.insert(alerta.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getAlertaBaseDados(tabela: TabelaAlertas, id: Long): Alerta {
        val cursor = tabela.query(
            TabelaAlertas.TODAS_COLUNAS,
            "${TabelaAlertas.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Alerta.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados(){
       getAppContext().deleteDatabase(BDCovidOpenHelper.Nome_Base_Dados)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

 //==========================================================
 //Tabela Distrito
 //=========================================================
    @Test
    fun consegueInserirDistritos(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Guarda")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        db.close()

    }

    @Test
    fun consegueAlterarDistrito(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Viana do Castelo")
        distrito.id = insereDistrito(tabelaDistritos, distrito)
        distrito.nome_distrito = "PORTO"

        val registoAlterados = tabelaDistritos.update(
            distrito.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(distrito.id.toString())
        )

        assertEquals(1, registoAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarDistritos(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Porto")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val registosEliminados= tabelaDistritos.delete(
            "${BaseColumns._ID}=?",
            arrayOf(distrito.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerDistritos() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaDistritos = TabelaDistritos(db)

        val distrito = Distrito(nome_distrito = "Viseu")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        assertEquals(distrito, getDistritoBaseDados(tabelaDistritos, distrito.id))

        db.close()
    }



    //==========================================================
    //Tabela Pessoa
    //=========================================================

    @Test
    fun consegueInserirPessoas(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Guimarães")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Carlos Miguel", sexo="Masculino", data_nascimento= Date(1990-1900,10,20),
            telemovel = "990257414", id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        assertEquals(pessoa, getPessoaBaseDados(tabelaPessoas, pessoa.id))
        db.close()

    }


    @Test
    fun consegueAlterarPessoas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Beja")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Castelo Branco")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Maria Madalena", sexo = "Feminino", data_nascimento= Date(1989-1900,10,20) ,telemovel ="960257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        pessoa.nome = "Marcos Sousa"
        pessoa.sexo = "Masculino"
        pessoa.data_nascimento = Date(2020-1900,10,20)
        pessoa.telemovel ="990252489"
        pessoa.id_estrang_distrito=distritoNovo.id
        pessoa.nomeDistrito = distritoNovo.nome_distrito

        val registosAlterados = tabelaPessoas.update(
            pessoa.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoa.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(pessoa, getPessoaBaseDados(tabelaPessoas, pessoa.id))

        db.close()
    }


    @Test
    fun consegueEliminarPessoas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Bragança")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "José Gama", sexo = "Masculino", data_nascimento= Date(1975-1900,12,29),telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val registosEliminados = tabelaPessoas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pessoa.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerPessoas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Leiria")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Joaquim Magico", sexo = "Masculino",
            data_nascimento= Date(1987-1900,7,20) ,telemovel ="990259012",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        assertEquals(pessoa, getPessoaBaseDados(tabelaPessoas, pessoa.id))

        db.close()
    }



    //==========================================================
    //Tabela Teste
    //=========================================================

    @Test
    fun consegueInserirTestes(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Faro")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Francisca Visconde", sexo="Feminino",
            data_nascimento= Date(1997-1900,10,20),telemovel ="927257460",
            id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 38.0F, sintomas= "Febre", estado_saude= "bom",
            data_teste= Date(2020-1900,10,20),
            id_estrang_pessoas= pessoa.id, nomePessoa = pessoa.nome)
        teste.id = insereTeste(tabelaTestes, teste)

        assertEquals(teste, getTesteBaseDados(tabelaTestes, teste.id))
        db.close()

    }

    @Test
    fun consegueAlterarTestes() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Seixal")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Setubal")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoaAtual = Pessoa(nome = "Matias", sexo = "Masculino",
            data_nascimento= Date(1990-1900,10,20),telemovel ="990257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoaAtual.id = inserePesssoa(tabelaPessoas, pessoaAtual)

        val pessoaNova = Pessoa(nome = "Juka Costa", sexo = "Masculino", data_nascimento= Date(2001-1900,10,20),telemovel ="990257414",
            id_estrang_distrito = distritoNovo.id, nomeDistrito = distritoNovo.nome_distrito)
        pessoaNova.id = inserePesssoa(tabelaPessoas, pessoaNova)



        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 30.0f, sintomas = "Nenhum", estado_saude = "Bom",
            data_teste= Date(2020-1900,10,20),id_estrang_pessoas = pessoaNova.id, nomePessoa = pessoaNova.nome)
        teste.id = insereTeste(tabelaTestes, teste)

        teste.temperatura = 40.0f
        teste.sintomas = "Gripe"
        teste.estado_saude= "doente"
        teste.data_teste= Date(2021-1900,4,29)
        teste.id_estrang_pessoas=pessoaNova.id
        teste.nomePessoa = pessoaNova.nome


        val registosAlterados = tabelaTestes.update(
            teste.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(teste.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(teste, getTesteBaseDados(tabelaTestes, teste.id))

        db.close()
    }

    @Test
    fun consegueEliminarTeste() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Figueira da Foz")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Lucio", sexo = "Masculino",
            data_nascimento= Date(1970-1900,10,20),telemovel ="990257237",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTeste = TabelaTestes(db)
        val teste = Teste(temperatura=39.0f, sintomas = "Febre",
            estado_saude = "Doente",data_teste= Date(2020-1900,10,20),
            id_estrang_pessoas = pessoa.id,nomePessoa = pessoa.nome)
        teste.id = insereTeste(tabelaTeste, teste)

        val registosEliminados = tabelaTeste.delete(
            "${BaseColumns._ID}=?",
            arrayOf(teste.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerTeste() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Santarém")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Pedro Cardoso", sexo = "Masculino",
            data_nascimento= Date(1999-1900,10,20) ,telemovel ="990257814",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste( temperatura = 36.5f, sintomas = "nenhum",estado_saude = "bom" ,data_teste= Date(2020-1900,10,20),
            id_estrang_pessoas = pessoa.id, nomePessoa = pessoa.nome)
        teste.id = insereTeste(tabelaTestes, teste)

        assertEquals(teste, getTesteBaseDados(tabelaTestes,teste.id))

        db.close()
    }


    //==========================================================
    //Tabela Notificacao
    //=========================================================


    @Test
    fun consegueInserirNotificacao(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Terras Novas")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Castro Gomes", sexo="Masculino", data_nascimento= Date(2004-1900,10,20),telemovel ="990257414",
            id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 38.0F, sintomas= "Febre", estado_saude= "doente",
            data_teste= Date(2020-1900,10,20), id_estrang_pessoas= pessoa.id,nomePessoa = pessoa.nome)
        teste.id = insereTeste(tabelaTestes, teste)

        val tabelaAlertas = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Vermelho", descricao = "Infectado")
        alerta.id = insereAlerta(tabelaAlertas, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Positivo", id_estrang_testes= teste.id,
            id_estrang_alertas = alerta.id,nomeAlerta = alerta.nome_alerta, nomeExternoPessoa = teste.nomePessoa)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao)

        assertEquals(notificacao, getNotificacaoBaseDados(tabelaNotificacao, notificacao.id))
        db.close()

    }


    @Test
    fun consegueAlterarNotificacao() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Abrantes")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Braga")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaAlerta = TabelaAlertas(db)

        val alertaAtual = Alerta(nome_alerta = "Verde",descricao = "Negativo" )
        alertaAtual.id = insereAlerta(tabelaAlerta, alertaAtual)

        val alertaNovo = Alerta(nome_alerta = "Vermelho", descricao = "Positivo")
        alertaNovo.id = insereAlerta(tabelaAlerta, alertaNovo)


        val tabelaPessoas = TabelaPessoas(db)
        val pessoaAtual = Pessoa(nome = "Romeu Souza", sexo = "Masculino", data_nascimento= Date(1979-1900,10,20),telemovel ="990257302",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoaAtual.id = inserePesssoa(tabelaPessoas, pessoaAtual)

        val pessoaNova = Pessoa(nome = "Rui Costa", sexo = "Masculino", data_nascimento= Date(2001-1900,10,20),telemovel ="990255633",
            id_estrang_distrito = distritoNovo.id, nomeDistrito = distritoNovo.nome_distrito)
        pessoaNova.id = inserePesssoa(tabelaPessoas, pessoaNova)


        val tabelaTestes = TabelaTestes(db)
        val testeAtual = Teste(temperatura = 30.0f, sintomas = "Nenhum", estado_saude = "Bom",
            data_teste= Date(2020-1900,10,20),id_estrang_pessoas = pessoaAtual.id,
            nomePessoa = pessoaAtual.nome)
           testeAtual.id = insereTeste(tabelaTestes, testeAtual)

        val testeNovo = Teste(temperatura = 50.0f, sintomas = "Tosse", estado_saude = "Doente",
            data_teste= Date(2019-1900,11,28),id_estrang_pessoas = pessoaNova.id, nomePessoa = pessoaNova.nome)
        testeNovo.id = insereTeste(tabelaTestes, testeNovo)



        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Negativo",id_estrang_testes = testeNovo.id,
            id_estrang_alertas = alertaAtual.id, nomeAlerta = alertaAtual.nome_alerta,
            nomeExternoPessoa = testeAtual.nomePessoa)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao)


        notificacao.resultado="Positivo"
        notificacao.id_estrang_testes=testeNovo.id
        notificacao.nomeAlerta = alertaNovo.nome_alerta
        notificacao.nomeExternoPessoa = testeNovo.nomePessoa

        val registosAlterados = tabelaNotificacao.update(
            notificacao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(notificacao.id.toString())
        )

        assertEquals(1, registosAlterados)

     //   assertEquals(notificacao, getNotificacaoBaseDados(tabelaNotificacao, notificacao.id))

        db.close()
    }


    @Test
    fun consegueEliminarNotificacao() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Tomar")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Celiza Silva", sexo = "Feminino",data_nascimento= Date(1994-1900,1,15), telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTeste = TabelaTestes(db)
        val teste = Teste(temperatura=29.0f, sintomas = "Nenhum", estado_saude = "Bom", data_teste= Date(2020-1900,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTeste, teste)

        val tabelaAlerta = TabelaAlertas(db)
        val alerta = Alerta(nome_alerta= "Verde", descricao = "Não Infectivo")
        alerta.id = insereAlerta(tabelaAlerta, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Negativo", id_estrang_testes = teste.id,
            id_estrang_alertas = alerta.id, nomeAlerta = alerta.nome_alerta, nomeExternoPessoa = teste.nomePessoa)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao )

        val registosEliminados = tabelaNotificacao.delete(
            "${BaseColumns._ID}=?",
            arrayOf(notificacao.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerNotificacao() {
        val db = getBDCovidOpenHelper().writableDatabase


        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Évora")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Josias Afonso", sexo = "Masculino",data_nascimento = Date(1993-1900,10,7) ,telemovel ="927456345",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste( temperatura = 36.5f, sintomas = "nenhum",estado_saude = "bom" , data_teste= Date(2020-1900,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        val tabelaAlertas = TabelaAlertas(db)
        val alerta = Alerta(nome_alerta = "Verde", descricao = "Não Infectado")
        alerta.id = insereAlerta(tabelaAlertas, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Negativo" ,id_estrang_testes = teste.id,
            id_estrang_alertas = alerta.id, nomeAlerta = alerta.nome_alerta, nomeExternoPessoa = teste.nomePessoa)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao)

        assertEquals(notificacao, getNotificacaoBaseDados(tabelaNotificacao, notificacao.id))

        db.close()
    }


    //==========================================================
    //Tabela Alerta
    //=========================================================
    @Test
    fun consegueInserirAlertas(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaAlertas = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Verde", descricao = "Não infectado")
        alerta.id = insereAlerta(tabelaAlertas, alerta)

        db.close()

    }

    @Test
    fun consegueAlterarAlerta(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaAlerta = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Laranja", descricao = "Esteve perto de um infectado")
        alerta.id = insereAlerta(tabelaAlerta, alerta)
        alerta.nome_alerta = "Verde"
        alerta.descricao="Não infectado"

        val registoAlterados = tabelaAlerta.update(
            alerta.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(alerta.id.toString())
        )

        assertEquals(1, registoAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarAlerta(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaAlerta = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Verde", descricao = "Não Infectado")
        alerta.id = insereAlerta(tabelaAlerta, alerta)

        val registosEliminados= tabelaAlerta.delete(
            "${BaseColumns._ID}=?",
            arrayOf(alerta.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    @Test
    fun consegueLerAlerta() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaAlerta = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Amarelo", descricao = "Esteve Perto de um infectado")
        alerta.id = insereAlerta(tabelaAlerta, alerta)

        assertEquals(alerta, getAlertaBaseDados(tabelaAlerta, alerta.id))

        db.close()
    }



}