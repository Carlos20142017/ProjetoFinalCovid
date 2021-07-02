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

        val distrito = Distrito(nome_distrito = "Guarda")
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
        val distrito = Distrito(nome_distrito = "Guarda")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Carlos", sexo="Masculino", data_nascimento= Date(2020,10,20),
            telemovel = "990257414", id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        assertEquals(pessoa, getPessoaBaseDados(tabelaPessoas, pessoa.id))
        db.close()

    }


    @Test
    fun consegueAlterarPessoas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Guarda")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Aveiro")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20) ,telemovel ="990257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        pessoa.nome = "Marcos Sousa"
        pessoa.sexo = "Masculino"
        pessoa.data_nascimento = Date(2020,10,20)
        pessoa.telemovel ="990257414"
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
        val distrito = Distrito(nome_distrito = "Aveiro")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20),telemovel ="990257414",
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
        val distrito = Distrito(nome_distrito = "Lisboa")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Jose", sexo = "Masculino",data_nascimento= Date(2020,10,20) ,telemovel ="990257414",
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
        val pessoa = Pessoa(nome = "Maria", sexo="Feminino", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 38.0F, sintomas= "Febre", estado_saude= "bom", data_teste= Date(2020,10,20), id_estrang_pessoas= pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        assertEquals(teste, getTesteBaseDados(tabelaTestes, teste.id))
        db.close()

    }

    @Test
    fun consegueAlterarTestes() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Faro")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Leiria")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        pessoa.nome = "Tomé"
        pessoa.sexo = "Masculino"
        pessoa.data_nascimento = Date(2020,10,20)
        pessoa.telemovel ="990257414"
        pessoa.id_estrang_distrito=distritoNovo.id
        pessoa.nomeDistrito = distritoNovo.nome_distrito


        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 0.0f, sintomas = "?", estado_saude = "?",data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        teste.temperatura = 40.0f
        teste.sintomas = "Gripe"
        teste.estado_saude= "doente"
        teste.data_teste= Date(2020,10,20)
        teste.id_estrang_pessoas=pessoa.id


        val registosAlterados = tabelaTestes.update(
            teste.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pessoa.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(teste, getTesteBaseDados(tabelaTestes, teste.id))

        db.close()
    }

    @Test
    fun consegueEliminarTeste() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Leiria")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTeste = TabelaTestes(db)
        val teste = Teste(temperatura=0.0f, sintomas = "?", estado_saude = "?",data_teste= Date(2020,10,20), id_estrang_pessoas = pessoa.id)
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
        val distrito = Distrito(nome_distrito = "Beja")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Jose", sexo = "Masculino",data_nascimento= Date(2020,10,20) ,telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste( temperatura = 36.5f, sintomas = "nenhum",estado_saude = "bom" ,data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
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
        val distrito = Distrito(nome_distrito = "Guarda")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "Castro", sexo="Masculino", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito=distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 38.0F, sintomas= "Febre", estado_saude= "doente",data_teste= Date(2020,10,20), id_estrang_pessoas= pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        val tabelaAlertas = TabelaAlertas(db)

        val alerta = Alerta(nome_alerta = "Vermelho", descricao = "Infectado")
        alerta.id = insereAlerta(tabelaAlertas, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Positivo", id_estrang_testes= teste.id, id_estrang_alertas = alerta.id)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao)

        assertEquals(notificacao, getNotificacaoBaseDados(tabelaNotificacao, notificacao.id))
        db.close()

    }


    @Test
    fun consegueAlterarNotificacao() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)

        val distritoAtual = Distrito(nome_distrito = "Guarda")
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val distritoNovo = Distrito(nome_distrito = "Almada")
        distritoNovo.id = insereDistrito(tabelaDistritos, distritoNovo)

        val tabelaAlerta = TabelaAlertas(db)

        val alertaAtual = Alerta(nome_alerta = "Verde",descricao = "Negativo" )
        distritoAtual.id = insereDistrito(tabelaDistritos, distritoAtual)

        val alertaNovo = Alerta(nome_alerta = "Vermelho", descricao = "Positivo")
        alertaNovo.id = insereAlerta(tabelaAlerta, alertaNovo)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20), telemovel ="990257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        pessoa.nome = "Guilherme Costa"
        pessoa.sexo = "Masculino"
        pessoa.data_nascimento =Date(2020,10,20)
        pessoa.telemovel ="990257414"
        pessoa.id_estrang_distrito=distritoNovo.id
        pessoa.nomeDistrito = distritoNovo.nome_distrito


        val tabelaTestes = TabelaTestes(db)
        val teste = Teste(temperatura = 0.0f, sintomas = "?", estado_saude = "?",data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        teste.temperatura = 40.0f
        teste.sintomas = "Gripe"
        teste.estado_saude= "doente"
        teste.data_teste= Date(2020,10,20)
        teste.id_estrang_pessoas=pessoa.id

        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "?",id_estrang_testes = teste.id, id_estrang_alertas = alertaAtual.id)
        notificacao.id = insereNotificacao(tabelaNotificacao, notificacao)


        notificacao.resultado="Positivo"
        notificacao.id_estrang_testes=teste.id


        val registosAlterados = tabelaNotificacao.update(
            notificacao.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(notificacao.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(notificacao, getNotificacaoBaseDados(tabelaNotificacao, notificacao.id))

        db.close()
    }


    @Test
    fun consegueEliminarNotificacao() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaDistritos = TabelaDistritos(db)
        val distrito = Distrito(nome_distrito = "Lisboa")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?",data_nascimento= Date(2020,10,20), telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTeste = TabelaTestes(db)
        val teste = Teste(temperatura=0.0f, sintomas = "?", estado_saude = "?", data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTeste, teste)

        val tabelaAlerta = TabelaAlertas(db)
        val alerta = Alerta(nome_alerta= "Verde", descricao = "Não Infectivo")
        alerta.id = insereAlerta(tabelaAlerta, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "?", id_estrang_testes = teste.id, id_estrang_alertas = alerta.id)
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
        val distrito = Distrito(nome_distrito = "Lisboa")
        distrito.id = insereDistrito(tabelaDistritos, distrito)

        val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Josias Afonso", sexo = "Masculino",data_nascimento = Date(1993,10,7) ,telemovel ="927456345",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste( temperatura = 36.5f, sintomas = "nenhum",estado_saude = "bom" , data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)

        val tabelaAlertas = TabelaAlertas(db)
        val alerta = Alerta(nome_alerta = "Verde", descricao = "Não Infectado")
        alerta.id = insereAlerta(tabelaAlertas, alerta)


        val tabelaNotificacao = TabelaNotificacao(db)
        val notificacao = Notificacao( resultado = "Negativo" ,id_estrang_testes = teste.id, id_estrang_alertas = alerta.id)
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