import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    private LocalDate parseDataDeNascimento(String dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Formato de data inválido. Use dd/MM/yyyy.");
            return null;
        }
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

