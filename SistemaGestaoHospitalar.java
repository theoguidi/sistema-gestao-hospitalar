// Classe que representa um paciente na fila de atendimento
class PacienteAtendimento {
    private String nome;
    private int codigoSenha;

    public PacienteAtendimento(String nome, int codigoSenha) {
        this.nome = nome;
        this.codigoSenha = codigoSenha;
    }

    public int getCodigoSenha() {
        return codigoSenha;
    }

    public String getNome() {
        return nome;
    }
}

// Classe responsável pelo gerenciamento da fila de atendimento
class GestorFila {
    private Queue<PacienteAtendimento> filaPacientes = new LinkedList<>();

    public void adicionarPaciente(PacienteAtendimento paciente) {
        filaPacientes.add(paciente);
        System.out.println("Paciente " + paciente.getNome() + " recebeu a senha " + paciente.getCodigoSenha() + " e foi adicionado à fila.");
    }

    public PacienteAtendimento chamarProximoPaciente() {
        return filaPacientes.poll(); // Retira e retorna o primeiro da fila
    }

    public boolean haPacientesNaFila() {
        return !filaPacientes.isEmpty();
    }
}

// Classe que gera senhas de forma sequencial
class ControleSenhas {
    private int contadorSenhas = 1;

    public int gerarNovaSenha() {
        return contadorSenhas++;
    }
}

// Classe que mantém um histórico de todos os atendimentos
class RegistroAtendimentos {
    private Queue<PacienteAtendimento> historicoPacientes = new LinkedList<>();

    public void adicionarRegistro(PacienteAtendimento paciente) {
        historicoPacientes.add(paciente);
    }

    public void exibirHistorico() {
        System.out.println("\nHistórico de atendimentos:");
        for (PacienteAtendimento p : historicoPacientes) {
            System.out.println("Senha: " + p.getCodigoSenha() + " - Paciente: " + p.getNome());
        }
    }
}

// Classe principal do sistema
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SistemaGestaoHospitalar {
    public static void main(String[] args) {
        Scanner entradaUsuario = new Scanner(System.in);
        ControleSenhas controleSenhas = new ControleSenhas();
        GestorFila filaPacientes = new GestorFila();
        RegistroAtendimentos historicoAtendimentos = new RegistroAtendimentos();

        while (true) {
            System.out.println("\nMenu do Sistema:");
            System.out.println("1 - Registrar novo paciente");
            System.out.println("2 - Chamar próximo paciente");
            System.out.println("3 - Exibir histórico de atendimentos");
            System.out.println("4 - Encerrar sistema");
            System.out.print("Escolha uma opção: ");
            int opcao = entradaUsuario.nextInt();
            entradaUsuario.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    System.out.print("Nome do paciente: ");
                    String nomePaciente = entradaUsuario.nextLine();
                    PacienteAtendimento novoPaciente = new PacienteAtendimento(nomePaciente, controleSenhas.gerarNovaSenha());
                    filaPacientes.adicionarPaciente(novoPaciente);
                    break;
                case 2:
                    if (filaPacientes.haPacientesNaFila()) {
                        PacienteAtendimento pacienteAtendido = filaPacientes.chamarProximoPaciente();
                        historicoAtendimentos.adicionarRegistro(pacienteAtendido);
                        System.out.println("Chamando senha: " + pacienteAtendido.getCodigoSenha() + " - Paciente: " + pacienteAtendido.getNome());
                    } else {
                        System.out.println("Nenhum paciente aguardando atendimento.");
                    }
                    break;
                case 3:
                    historicoAtendimentos.exibirHistorico();
                    break;
                case 4:
                    System.out.println("Finalizando o sistema...");
                    entradaUsuario.close();
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
