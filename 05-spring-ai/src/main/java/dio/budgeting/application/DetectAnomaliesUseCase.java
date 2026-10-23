package dio.budgeting.application;

import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.ai.chat.model.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetectAnomaliesUseCase {

    private final TransactionRepository repository;

    public DetectAnomaliesUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public record AnomalyReport(int totalTransactionsAnalyzed, double meanAmount, double standardDeviation, List<Transaction> outliers) {}

    @Tool("Analisa o histórico de transações e detecta gastos anômalos ou fora do padrão estatístico do usuário")
    public AnomalyReport execute() {
        List<Transaction> transactions = repository.findAll();
        
        if (transactions.isEmpty()) {
            return new AnomalyReport(0, 0.0, 0.0, List.of());
        }

        // Calcula a média
        double mean = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0);

        // Calcula a variância e o desvio padrão
        double variance = transactions.stream()
                .mapToDouble(t -> Math.pow(t.getAmount() - mean, 2))
                .average()
                .orElse(0.0);
        double stdDev = Math.sqrt(variance);

        // Define o limite (limiar) de anomalia: Média + 1.5 * Desvio Padrão
        double anomalyThreshold = mean + (1.5 * stdDev);

        // Filtra as transações que ultrapassaram o limite
        List<Transaction> outliers = transactions.stream()
                .filter(t -> t.getAmount() > anomalyThreshold)
                .toList();

        return new AnomalyReport(transactions.size(), mean, stdDev, outliers);
    }
}