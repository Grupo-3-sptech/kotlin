package app

import com.github.britooo.looca.api.core.Looca
import org.apache.commons.dbcp2.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.time.LocalDateTime
import javax.swing.JOptionPane


open class Main {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val looka1 = LookaDados()
            looka1.all()
        }
    }
}


class LookaDados {
    val looca = Looca()
    val bdInter: JdbcTemplate

    //id do processador de placeholder por enquanto.
    var id = Looca().processador.id

    init {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        val serverName = "localhost"
        val mydatabase = "medconnect"
        dataSource.username = "admin"
        dataSource.password = "admin"
        dataSource.url = "jdbc:mysql://$serverName/$mydatabase"
        bdInter = JdbcTemplate(dataSource)
    }

    fun all() {

        var aPrimeiraVez: Boolean = ver()


        if (aPrimeiraVez == false) {
            while (true) {
                //   python()
                sistema()
                memoria()
                processador()
                grupoDeDiscos()
                grupoDeServicos()
                grupoDeProcessos()
                Dispositivo()
                rede()
                janelas()
                Thread.sleep(20 * 1000)
            }
        } else {
            cadastroUsu()
        }


    }

    //fun python(){
    //var arqPyConn = "SolucaoConn.py"
    //var pyExec: Process? = null
    //pyExec = Runtime.getRuntime().exec("python $arqPyConn")
    //}


    fun cadastroUsu() {
        var autorizacao = false

        var email: String = JOptionPane.showInputDialog("insira o seu email")
        var senha: String = JOptionPane.showInputDialog("insira sua senha")


        var usu = bdInter.queryForObject(
            """
    select fkHospital from Funcionarios
    where email = '$email' AND senha = '$senha'
    """,
            Int::class.java
        )


        if (usu != null) {
            autorizacao = true
        }


        if (autorizacao == true) {
            JOptionPane.showMessageDialog(
                null,
                "arraste o get-pip.py para a pasta public execute o arquivo InstalarPython.bat como adimistrador em seguida o InstalarPip.bat ambos como adimistrador, a instalao já está começando"
            )
//vamos ter que pensar regra de negocio ou script para o pythn ser instalado "aqui"
            cad(usu)
        } else {
            println("problema na autenticação")
        }
    }

    fun cad(fkHospital: Int) {

        bdInter.execute(
            """
                INSERT INTO RoboCirurgiao (modelo, fabricacao, fkStatus, idProcess, fkHospital) 
VALUES ('Modelo A', '${looca.processador.fabricante}', 1, '$id', $fkHospital);
                
                """
        )
        println("parabéns robo cadastrado baixando agora a solução MEDCONNECT")



        solucao()
        all()
    }


    fun downloadArq(url: URL, nomeArquivoDoPip: String) {
//funão de baixar arquivo da net
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(nomeArquivoDoPip).use { fos -> //
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE) //
                }
            }
        }
    }


    fun ver(): Boolean {
        //função que verifica se a maquina já foi usada antes


        val idRobo = bdInter.queryForObject(
            """
    select count(*) as count from RoboCirurgiao where idProcess = '$id'
    """,
            Int::class.java,
        )

        if (idRobo == 0) {
            return true
        } else {
            return false
        }


    }


    fun sistema() {
        val sistema = looca.sistema

        println(sistema)

        var fabricante = sistema.fabricante


        var incializando = sistema.inicializado

        var sistemaOperacional = sistema.sistemaOperacional

        println(looca.sistema.sistemaOperacional)

        var arquitetura = sistema.arquitetura

        var permissao = sistema.permissao

        var tempDeAtividade = sistema.tempoDeAtividade


    }

    fun memoria() {
        val memoria = looca.memoria

        var emUso = memoria.emUso

        var disponivel = memoria.disponivel

        var total = memoria.total
    }

    fun processador() {

        var processador = looca.processador

        var fabricante = processador.fabricante


        var frequencia = processador.frequencia

        var nome = processador.nome


        var identificador = processador.identificador


        var microarquitetura = processador.microarquitetura

        var numeroCpuFis = processador.numeroCpusFisicas

        var numCpuLogica = processador.numeroCpusLogicas

        var uso = processador.uso

        var numPacotFisico = processador.numeroPacotesFisicos

        //    bdInter.execute(
        //      """
        //INSERT INTO Registros (fkRoboRegistro, HorarioDado, dado, fkComponente)
//VALUES
        // (1, '${LocalDateTime.now()}', ${uso}, 1);

        //      """
        //   )
    }

    fun grupoDeDiscos() {
        val grupoDeDiscos = looca.grupoDeDiscos


        var qtdDeDisco = grupoDeDiscos.quantidadeDeDiscos
        //I live in the Rua hadock lobo Building on West 595 Street on the 2nd floor. My name is Enzo I’m 18 years old. There is an idea of a Enzo. Some kind of abstraction. But there is no real me.

        var discos = grupoDeDiscos.discos

        var volumes = grupoDeDiscos.volumes

        var tamanhoTotal = grupoDeDiscos.tamanhoTotal

        var qtdVolumes = grupoDeDiscos.quantidadeDeVolumes

        var nome = discos[0].nome

        var serial = discos[0].serial

    }

    fun grupoDeServicos() {
        val grupoDeServicos = looca.grupoDeServicos
        var servicos = grupoDeServicos.servicos
        var nome = servicos[0].nome
        var estado = servicos[0].estado
        var pid = servicos[0].pid
        var servicosAtivos = grupoDeServicos.servicosAtivos
        var sevicosInativos = grupoDeServicos.servicosInativos
        var totalDeServiços = grupoDeServicos.totalDeServicos
        var totalServicosAtivos = grupoDeServicos.totalServicosAtivos
        var totalServicosInativos = grupoDeServicos.totalServicosInativos
    }

    fun grupoDeProcessos() {
        val grupoDeProcessos = looca.grupoDeProcessos
        var processos = grupoDeProcessos.processos
        var totalProcessos = grupoDeProcessos.totalProcessos
        var totalThreads = grupoDeProcessos.totalThreads
    }


    fun Dispositivo() {
        val DispositivoUsbGp = looca.dispositivosUsbGrupo
        var totalConectados = DispositivoUsbGp.totalDispositvosUsbConectados
        var dispositivosUsb = DispositivoUsbGp.dispositivosUsb
        var dispositivosUsbConectados = DispositivoUsbGp.dispositivosUsbConectados
        var totalDispositvosUsb = DispositivoUsbGp.totalDispositvosUsb

        val idRobo = bdInter.queryForObject(
            """
    select idRobo from RoboCirurgiao where idProcess = '$id'
    """,
            Int::class.java,
        )

        for (dispositivo in dispositivosUsb) {
            var nome = dispositivo.nome
            var idProduto = dispositivo.idProduto
            var fornecedor = dispositivo.forncecedor


            bdInter.execute(
                """
            INSERT INTO dispositivos_usb (nome, dataHora, id_produto, fornecedor, conectado, fkRoboUsb)
            VALUES ('$nome', '${LocalDateTime.now()}', '$idProduto', '$fornecedor', 1, $idRobo);
            """
            )
        }
    }



    fun rede() {
        val rede = looca.rede
        println(rede)
        var parametros = rede.parametros
        var grupoDeInterfaces = rede.grupoDeInterfaces
    }

    fun janelas() {
        val janela = looca.grupoDeJanelas
        var janelas = janela.janelas
        var janelasVisiveis = janela.janelasVisiveis
        var totalJanelas = janela.totalJanelas
    }

    fun solucao() {
        val roboId = bdInter.queryForObject(
            """
    select idRobo from RoboCirurgiao where idProcess = '$id'
    """,
            Int::class.java,
        )


        var os: String = looca.sistema.sistemaOperacional

        if (os == "Windows") {
            val url = URL("https://bootstrap.pypa.io/get-pip.py")
            val nomeArquivoDoPip = "get-pip.py"
            downloadArq(url, nomeArquivoDoPip)
            println("Arquivo baixado com sucesso: $nomeArquivoDoPip")

            val nomeBash = "InstalarPip.bat"
            val arqBash = File(nomeBash)
            arqBash.writeText(
                "cd C:\\Users\\Public" +
                        "py get-pip.py"
            )
            val nomeBash2 = "InstalarDepPy.bat"
            val arqBash2 = File(nomeBash2)
            arqBash2.writeText(
                "cd C:\\Users\\Public" +
                        "pip install psutil" +
                        "pip install mysql-connector-python"+
                        "pip install ping3"
            )
            val nomeBash6 = "SolucaoMedConn.bat"
            val arqBash6 = File(nomeBash6)
            arqBash6.writeText(
                "py SolucaoConn.py"
            )


            val nomePy = "SolucaoConn.py"
            val arqPy = File(nomePy)
            arqPy.writeText(
                "from mysql.connector import connect\n" +
                        "import psutil\n" +
                        "import platform\n" +
                        "import time\n" +
                        "import mysql.connector\n" +
                        "from datetime import datetime\n" +
                        "import ping3\n" +
                        "\n" +
                        "def mysql_connection(host, user, passwd, database=None):\n" +
                        "    connection = connect(\n" +
                        "        host=host,\n" +
                        "        user=user,\n" +
                        "        passwd=passwd,\n" +
                        "        database=database\n" +
                        "    )\n" +
                        "    return connection\n" +
                        "\n" +
                        "def bytes_para_gb(bytes_value):\n" +
                        "    return bytes_value / (1024 ** 3)\n" +
                        "\n" +
                        "def milissegundos_para_segundos(ms_value):\n" +
                        "    return ms_value / 1000\n" +
                        "\n" +
                        "connection = mysql_connection('localhost', 'admin', 'admin', 'medconnect')\n" +
                        "\n" +
                        "#Disco\n" +
                        "\n" +
                        "meu_so = platform.system()\n" +
                        "if(meu_so == \"Linux\"):\n" +
                        "    nome_disco = '/'\n" +
                        "    disco = psutil.disk_usage(nome_disco)\n" +
                        "elif(meu_so == \"Windows\"):\n" +
                        "    nome_disco = 'C:\\\\'\n" +
                        "disco = psutil.disk_usage(nome_disco)\n" +
                        "discoPorcentagem = disco.percent\n" +
                        "discoTotal = \"{:.2f}\".format(bytes_para_gb(disco.total))\n" +
                        "discoUsado = \"{:.2f}\".format(bytes_para_gb(disco.used)) \n" +
                        "discoTempoLeitura = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[4])\n" +
                        "discoTempoEscrita = milissegundos_para_segundos(psutil.disk_io_counters(perdisk=False, nowrap=True)[5])\n" +
                        "\n" +
                        "ins = [discoPorcentagem, discoTotal, discoUsado, discoTempoLeitura, discoTempoEscrita]\n" +
                        "componentes = [10,11,12,13,14]\n" +
                        "\n" +
                        "horarioAtual = datetime.now()\n" +
                        "horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                        "\n" +
                        "cursor = connection.cursor()\n" +
                        "for i in range(len(ins)):\n" +
                        "        \n" +
                        "    dado = ins[i]\n" +
                        "        \n" +
                        "    componente = componentes[i]\n" +
                        "\n" +
                        "    \n" +
                        "    query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, ${roboId}, %s, %s)\"\n" +
                        "\n" +
                        "    \n" +
                        "    cursor.execute(query, (dado, componente,horarioFormatado))\n" +
                        "\n" +
                        "\n" +
                        "    connection.commit()\n" +
                        "\n" +
                        "print(\"\\nDisco porcentagem:\", discoPorcentagem,\n" +
                        "          \"\\nDisco total:\", discoTotal,\n" +
                        "          '\\nTempo de leitura do disco em segundos:', discoTempoLeitura,\n" +
                        "          '\\nTempo de escrita do disco em segundos:', discoTempoEscrita)\n" +
                        "\n" +
                        "\n" +
                        "while True:\n" +
                        "\n" +
                        "    #CPU\n" +
                        "    cpuPorcentagem = psutil.cpu_percent(None)\n" +
                        "    frequenciaCpuMhz = psutil.cpu_freq(percpu=False)\n" +
                        "    cpuVelocidadeEmGhz = \"{:.2f}\".format(frequenciaCpuMhz.current / 1000)\n" +
                        "    tempoSistema = psutil.cpu_times()[1] \n" +
                        "    processos = len(psutil.pids())\n" +
                        "\n" +
                        "    \n" +
                        "    #Memoria\n" +
                        "    memoriaPorcentagem = psutil.virtual_memory()[2]\n" +
                        "    memoriaTotal = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().total))\n" +
                        "    memoriaUsada = \"{:.2f}\".format(bytes_para_gb(psutil.virtual_memory().used))\n" +
                        "    memoriaSwapPorcentagem = psutil.swap_memory().percent\n" +
                        "    memoriaSwapUso = \"{:.2f}\".format(bytes_para_gb(psutil.swap_memory().used))\n" +
                        "    \n" +
                        "    \"\"\"\n" +
                        "    Por enquanto não será usado\n" +
                        "    for particao in particoes:\n" +
                        "        try:\n" +
                        "            info_dispositivo = psutil.disk_usage(particao.mountpoint)\n" +
                        "            print(\"Ponto de Montagem:\", particao.mountpoint)\n" +
                        "            print(\"Sistema de Arquivos:\", particao.fstype)\n" +
                        "            print(\"Dispositivo:\", particao.device)\n" +
                        "            print(\"Espaço Total: {0:.2f} GB\".format(info_dispositivo.total / (1024 ** 3)) )\n" +
                        "            print(\"Espaço Usado: {0:.2f} GB\".format(info_dispositivo.used / (1024 ** 3)) )\n" +
                        "            print(\"Espaço Livre: {0:.2f} GB\".format(info_dispositivo.free / (1024 ** 3)) )\n" +
                        "            print(\"Porcentagem de Uso: {0:.2f}%\".format(info_dispositivo.percent))\n" +
                        "            print()\n" +
                        "        except PermissionError as e:\n" +
                        "            print(f\"Erro de permissão ao acessar {particao.mountpoint}: {e}\")\n" +
                        "        except Exception as e:\n" +
                        "            print(f\"Erro ao acessar {particao.mountpoint}: {e}\")\n" +
                        "            \"\"\"\n" +
                        "    #Rede\n" +
                        "    interval = 1\n" +
                        "    statusRede = 0\n" +
                        "    network_connections = psutil.net_connections()\n" +
                        "    network_active = any(conn.status == psutil.CONN_ESTABLISHED for conn in network_connections)\n" +
                        "    bytes_enviados = psutil.net_io_counters()[0]\n" +
                        "    bytes_recebidos = psutil.net_io_counters()[1]\n" +
                        "    \n" +
                        "    destino = \"google.com\"  \n" +
                        "    latencia = ping3.ping(destino) * 1000\n" +
                        "    \n" +
                        "    if latencia is not None:\n" +
                        "        print(f\"Latência para {destino}: {latencia:.2f} ms\")\n" +
                        "    else:\n" +
                        "        print(f\"Não foi possível alcançar {destino}\")  \n" +
                        "\n" +
                        "    \n" +
                        "    if network_active:\n" +
                        "\n" +
                        "        print (\"A rede está ativa.\")\n" +
                        "        statusRede= 1\n" +
                        "    else:\n" +
                        "\n" +
                        "        print (\"A rede não está ativa.\")\n" +
                        "\n" +
                        "    #Outros\n" +
                        "    boot_time = datetime.fromtimestamp(psutil.boot_time()).strftime(\"%Y-%m-%d %H:%M:%S\")\n" +
                        "    print(\"A maquina está ligada desde: \",boot_time)\n" +
                        "\n" +
                        "    horarioAtual = datetime.now()\n" +
                        "    horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                        "    \n" +
                        "    ins = [cpuPorcentagem, cpuVelocidadeEmGhz, tempoSistema, processos, memoriaPorcentagem,\n" +
                        "           memoriaTotal, memoriaUsada, memoriaSwapPorcentagem, memoriaSwapUso, statusRede, latencia,\n" +
                        "           bytes_enviados, bytes_recebidos]\n" +
                        "    componentes = [1,2,3,4,5,6,7,8,9,15,16,17,18]\n" +
                        "    \n" +
                        "    cursor = connection.cursor()\n" +
                        "    \n" +
                        "    for i in range(len(ins)):\n" +
                        "        \n" +
                        "        dado = ins[i]\n" +
                        "        \n" +
                        "        componente = componentes[i]\n" +
                        "\n" +
                        "    \n" +
                        "        query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, ${roboId}, %s, %s)\"\n" +
                        "\n" +
                        "    \n" +
                        "        cursor.execute(query, (dado, componente,horarioFormatado))\n" +
                        "\n" +
                        "\n" +
                        "        connection.commit()\n" +
                        "       \n" +
                        "    print(\"\\nINFORMAÇÕES SOBRE PROCESSAMENTO: \")\n" +
                        "    print('\\nPorcentagem utilizada da CPU: ',cpuPorcentagem,\n" +
                        "          '\\nVelocidade da CPU: ',cpuVelocidadeEmGhz,\n" +
                        "          '\\nTempo de atividade da CPU: ', tempoSistema,\n" +
                        "          '\\nNumero de processos: ', processos,\n" +
                        "          '\\nPorcentagem utilizada de memoria: ', memoriaPorcentagem,\n" +
                        "          '\\nQuantidade usada de memoria: ', memoriaTotal,\n" +
                        "          '\\nPorcentagem usada de memoria Swap: ', memoriaSwapPorcentagem,\n" +
                        "          '\\nQuantidade usada de memoria Swap: ', memoriaSwapUso,\n" +
                        "          '\\nBytes enviados', bytes_enviados,\n" +
                        "          '\\nBytes recebidos', bytes_recebidos)\n" +
                        "   \n" +
                        "    \n" +
                        "       \n" +
                        "\n" +
                        "\n" +
                        "    time.sleep(5)\n" +
                        "\n" +
                        "cursor.close()\n" +
                        "connection.close()\n" +
                        "    \n"
            )

            val nomeBash1 = "InstalarPython.bat"
            val arqBash1 = File(nomeBash1)
            arqBash1.writeText(
                // escreve um comando de script para instalar o py usando chocolatey
                "@\"%SystemRoot%\\System32\\WindowsPowerShell\\v1.0\\powershell.exe\" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command \"iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))\" && SET \"PATH=%PATH%;%ALLUSERSPROFILE%\\chocolatey\\bin\"\n\n" +
                        "choco install python311 --params \"/C:\\Users\\Public\""
            )
        } else {
            //aqui é a instalção da solução se a maquina não for baseadinho windows
            val url = URL("https://bootstrap.pypa.io/get-pip.py")
            val nomeArquivoDoPip = "get-pip.py"
            downloadArq(url, nomeArquivoDoPip)
            println("Arquivo baixado com sucesso: $nomeArquivoDoPip")

            //boa sorte kayk fazendo esses .sh

            val nomeBashLinux = "InstalarPip.sh"
            val arqBashLinux = File(nomeBashLinux)
            arqBashLinux.writeText(
                ""
            )

            val nomeBash2Linux = "InstalarDepPy.sh"
            val arqBash2Linux = File(nomeBash2Linux)
            arqBash2Linux.writeText(
                ""
            )

            val nomeBash1Linux = "InstalarPython.sh"
            val arqBash1Linux = File(nomeBash1Linux)
            arqBash1Linux.writeText(
                // escreve um comando de script para instalar o py usando chocolatey
                ""
            )

            val nomePyLinux = "SolucaoConn.py"
            val arqPyLinux = File(nomePyLinux)
            arqPyLinux.writeText(
                "from mysql.connector import connect\n" +
                        "import psutil\n" +
                        "import platform\n" +
                        "import time\n" +
                        "import mysql.connector\n" +
                        "from datetime import datetime\n" +
                        "\n" +
                        "def mysql_connection(host, user, passwd, database=None):\n" +
                        "    connection = connect(\n" +
                        "        host=host,\n" +
                        "        user=user,\n" +
                        "        passwd=passwd,\n" +
                        "        database=database\n" +
                        "    )\n" +
                        "    return connection\n" +
                        "\n" +
                        "connection = mysql_connection('localhost', 'admin', 'admin', 'medconnect')\n" +
                        "\n" +
                        "while True:\n" +
                        "    memoria = psutil.virtual_memory()[2]\n" +
                        "    cpu = psutil.cpu_percent(None)\n" +
                        "    disco = psutil.disk_usage('/')[3]\n" +
                        "    interval = 1\n" +
                        "    statusRede = 0\n" +
                        "    network_connections = psutil.net_connections()\n" +
                        "\n" +
                        "    network_active = any(conn.status == psutil.CONN_ESTABLISHED for conn in network_connections)\n" +
                        "\n" +
                        "    \n" +
                        "\n" +
                        "    print (\"\\nINFORMAÇÕES SOBRE A REDE: \")\n" +
                        "\n" +
                        "    if network_active:\n" +
                        "\n" +
                        "        print (\"A rede está ativa.\")\n" +
                        "        statusRede= 1\n" +
                        "    else:\n" +
                        "\n" +
                        "        print (\"A rede não está ativa.\")\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "    cursor = connection.cursor()\n" +
                        "\n" +
                        "\n" +
                        "    horarioAtual = datetime.now()\n" +
                        "    horarioFormatado = horarioAtual.strftime('%Y-%m-%d %H:%M:%S')\n" +
                        "    \n" +
                        "    ins = [cpu, memoria, disco, statusRede]\n" +
                        "    componentes = [1,2,3,4]\n" +
                        "    cursor = connection.cursor()\n" +
                        "    \n" +
                        "    for i in range(len(ins)):\n" +
                        "        \n" +
                        "        dado = ins[i]\n" +
                        "        \n" +
                        "        componente = componentes[i]\n" +
                        "\n" +
                        "    \n" +
                        "        query = \"INSERT INTO Registros (dado, fkRoboRegistro, fkComponente, HorarioDado) VALUES (%s, $roboId %s, %s)\"\n" +
                        "\n" +
                        "    \n" +
                        "        cursor.execute(query, (dado, componente,horarioFormatado))\n" +
                        "\n" +
                        "\n" +
                        "        connection.commit()\n" +
                        "    print(\"\\nINFORMAÇÕES SOBRE PROCESSAMENTO: \")\n" +
                        "    print('Porcentagem utilizada do Processador: ',cpu,'\\nPorcentagem utilizada de memoria: ', memoria,'\\nPorcentagem do disco sendo utilizada:', disco,'\\nStatus da rede: ',statusRede)\n" +
                        "   \n" +
                        "    \n" +
                        "       \n" +
                        "\n" +
                        "\n" +
                        "    time.sleep(10)\n" +
                        "\n" +
                        "cursor.close()\n" +
                        "connection.close()\n" +
                        "    "
            )


        }


    }


}
