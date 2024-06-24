import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            System.out.println();
            System.out.println("Menu de Navegação:");
            System.out.println("1. Inserir funcionário");
            System.out.println("2. Apagar funcionário");
            System.out.println("3. Listar funcionários");
            System.out.println("4. Aumentar salário de todos os funcionários em 10%");
            System.out.println("5. Sair");
            System.out.println();
            System.out.print("Escolha uma opção: ");
            System.out.println();
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    inserirFuncionario(sc, funcionarios);
                    break;
                case 2:
                    apagarFuncionario(sc, funcionarios);
                    break;
                case 3:
                    listarFuncionarios(sc, funcionarios);
                    break;
                case 4:
                    aumentarSalario(funcionarios);
                    break;
                case 5:
                    continuar = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        sc.close();
    }

    private static void inserirFuncionario(Scanner sc, ArrayList<Funcionario> funcionarios) {
        System.out.println();
        System.out.println("Digite o nome do funcionário:");
        String nome = sc.nextLine();

        System.out.println("Digite a data de nascimento do funcionário no formato (dd/MM/yyyy):");
        String dataNascimentoStr = sc.nextLine();
        LocalDate dataNascimento = parseDataDeNascimento(dataNascimentoStr);

        System.out.println("Digite o salário do funcionário:");
        BigDecimal salario = sc.nextBigDecimal();
        sc.nextLine();

        System.out.println("Digite a função do funcionário:");
        String funcao = sc.nextLine();

        Funcionario funcionario = new Funcionario(nome, dataNascimento, salario, funcao);
        funcionarios.add(funcionario);
        System.out.println("Funcionário adicionado com sucesso.");
    }

    private static void apagarFuncionario(Scanner sc, ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado para apagar.");
            return;
        }

        System.out.println();
        System.out.println("Funcionários cadastrados:");
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario funcionario = funcionarios.get(i);
            System.out.println((i + 1) + ": " + funcionario.getNome() + " - " + funcionario.getFuncao());
        }

        System.out.println();
        System.out.println("Digite o índice do funcionário a ser apagado (1 a " + funcionarios.size() + "):");
        int indice = sc.nextInt();
        sc.nextLine();

        if (indice > 0 && indice <= funcionarios.size()) {
            Funcionario removido = funcionarios.remove(indice - 1);
            System.out.println();
            System.out.println("Funcionário " + removido.getNome() + " removido com sucesso.");
        } else {
            System.out.println();
            System.out.println("Índice inválido.");
        }
    }

    private static void listarFuncionarios(Scanner sc, ArrayList<Funcionario> funcionarios) {
        System.out.println();
        System.out.println("Menu de Listagem:");
        System.out.println("1. Listar todos os funcionários");
        System.out.println("2. Listar funcionários por função");
        System.out.println("3. Exibir valor total dos salários dos funcionários");
        System.out.println("4. Listar salários em múltiplos de salário mínimo");
        System.out.println("5. Exibir funcionário mais velho");
        System.out.println("6. Listar aniversariantes do mês");
        System.out.println("7. Retornar ao menu anterior");
        System.out.println();
        System.out.print("Escolha uma opção: ");
        System.out.println();
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1:
                listarFuncionariosPorNome(sc, funcionarios);
                break;
            case 2:
                listarFuncionariosPorFuncao(funcionarios);
                break;
            case 3:
                exibirTotalSalarios(funcionarios);
                break;
            case 4:
                imprimirSalariosMinimos(funcionarios);
                break;
            case 5:
                exibirFuncionarioMaisVelho(funcionarios);
                break;
            case 6:
                System.out.println();
                System.out.print("Digite os meses separados por vírgula (1 a 12): ");
                String mesesStr = sc.nextLine();
                imprimirAniversariantesMes(funcionarios, mesesStr);
                break;
            case 7:
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void listarFuncionariosPorCadastro(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("Funcionários cadastrados:");
        System.out.println("(por ordem de cadastro)");
        System.out.println("-----------");
        System.out.println();
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario funcionario = funcionarios.get(i);
            System.out.println();
            System.out.println((i + 1) + "º funcionário:");
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Salário: " + funcionario.getSalario());
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("-----------");
        }
    }

    private static void listarFuncionariosPorNome(Scanner sc, ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println();
        System.out.println("Menu de Listagem por Nome:");
        System.out.println("1. Listar por ordem de cadastro");
        System.out.println("2. Listar em ordem alfabética");
        System.out.println("3. Retornar ao menu anterior");
        System.out.println();
        System.out.print("Escolha uma opção: ");
        System.out.println();
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1:
                listarFuncionariosPorCadastro(funcionarios);
                break;
            case 2:
                listarFuncionariosPorOrdemAlfabetica(funcionarios);
                break;
            case 3:
                listarFuncionarios(sc, funcionarios);
                break;
            default:
                System.out.println();
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private static void listarFuncionariosPorOrdemAlfabetica(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        ArrayList<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        Collections.sort(funcionariosOrdenados, Comparator.comparing(Funcionario::getNome));

        System.out.println();
        System.out.println("Funcionários cadastrados em ordem alfabética:");
        System.out.println("-----------");
        System.out.println();
        for (int i = 0; i < funcionariosOrdenados.size(); i++) {
            Funcionario funcionario = funcionariosOrdenados.get(i);
            System.out.println();
            System.out.println((i + 1) + "º funcionário:");
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Salário: " + funcionario.getSalario());
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("-----------");

        }
    }

    private static void aumentarSalario(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado para aumentar o salário.");
            return;
        }

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal aumento = salarioAtual.multiply(new BigDecimal("0.10"));
            BigDecimal novoSalario = salarioAtual.add(aumento);
            funcionario.setSalario(novoSalario);
        }
        System.out.println();
        System.out.println("Salário de todos os funcionários aumentados em 10% com sucesso.");
    }

    private static void listarFuncionariosPorFuncao(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado para imprimir.");
            return;
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }

        System.out.println();
        System.out.println("Funcionários agrupados por função:");
        for (String funcao : funcionariosPorFuncao.keySet()) {
            System.out.println();
            System.out.println("Função: " + funcao);
            System.out.println("-----------");
            List<Funcionario> funcionariosDaFuncao = funcionariosPorFuncao.get(funcao);
            for (Funcionario func : funcionariosDaFuncao) {
                System.out.println("Nome: " + func.getNome());
                System.out.println("Data de Nascimento: " + func.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                System.out.println("Salário: " + func.getSalario());
                System.out.println("Função: " + func.getFuncao());
                System.out.println("-----------");
            }
        }
    }

    private static void exibirFuncionarioMaisVelho(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado para imprimir.");
            return;
        }

        Funcionario funcionarioMaisVelho = null;
        LocalDate hoje = LocalDate.now();
        int maiorIdade = Integer.MIN_VALUE;

        for (Funcionario funcionario : funcionarios) {
            int idade = Period.between(funcionario.getDataNascimento(), hoje).getYears();
            if (idade > maiorIdade) {
                maiorIdade = idade;
                funcionarioMaisVelho = funcionario;
            }
        }

        if (funcionarioMaisVelho != null) {
            System.out.println();
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + funcionarioMaisVelho.getNome());
            System.out.println("Idade: " + maiorIdade + " anos");
        } else {
            System.out.println();
            System.out.println("Não foi possível determinar o funcionário mais velho.");
        }
    }

    private static LocalDate parseDataDeNascimento(String dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            System.out.println();
            System.err.println("Formato de data inválido. Use dd/MM/yyyy.");
            return null;
        }
    }

    private static void exibirTotalSalarios(ArrayList<Funcionario> funcionarios) {
        BigDecimal totalSalarios = BigDecimal.ZERO;
        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário foi cadastrado.");
        } else {
            System.out.println();
            System.out.println("Total dos salários dos funcionários: " + NumberFormat.getCurrencyInstance().format(totalSalarios));
        }
    }

    private static void imprimirSalariosMinimos(ArrayList<Funcionario> funcionarios) {
        if (funcionarios.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum funcionário cadastrado para imprimir.");
            return;
        }

        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println();
        System.out.println("Salários em múltiplos de salário mínimo:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioFuncionario = funcionario.getSalario();
            BigDecimal multiplicadorSalarioMinimo = salarioFuncionario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + ": " + multiplicadorSalarioMinimo + " salários mínimos");
        }
    }

    private static void imprimirFuncionariosFiltrados(ArrayList<Funcionario> funcionarios, Predicate<Funcionario> filtro) {
        List<Funcionario> funcionariosFiltrados = funcionarios.stream()
                .filter(filtro)
                .collect(Collectors.toList());

        if (funcionariosFiltrados.isEmpty()) {
            System.out.println("Não há nenhum aniversariante no(s) mês(es) indicado(s).");
            return;
        }
        System.out.println();
        System.out.println("Funcionários filtrados:");
        for (Funcionario funcionario : funcionariosFiltrados) {
            System.out.println();
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("Salário: " + funcionario.getSalario());
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println("-----------");
        }
    }

    private static void imprimirAniversariantesMes(ArrayList<Funcionario> funcionarios, String mesesStr) {
        String[] mesesArray = mesesStr.split(",");
        Set<Integer> mesesSet = new HashSet<>();
        for (String mesStr : mesesArray) {
            try {
                int mes = Integer.parseInt(mesStr.trim());
                if (mes >= 1 && mes <= 12) {
                    mesesSet.add(mes);
                } else {
                    System.out.println();
                    System.out.println("Mês inválido: " + mes);
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("Formato de mês inválido: " + mesStr);
            }
        }

        if (mesesSet.isEmpty()) {
            System.out.println();
            System.out.println("Nenhum mês válido foi fornecido.");
            return;
        }

        Predicate<Funcionario> filtroMeses = funcionario ->
                mesesSet.contains(funcionario.getDataNascimento().getMonthValue());

        imprimirFuncionariosFiltrados(funcionarios, filtroMeses);
    }
}
