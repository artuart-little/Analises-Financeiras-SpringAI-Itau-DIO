package dio.budgeting.application;

import dio.budgeting.domain.Transaction;
import dio.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportAnalyticsUseCase {

    private final TransactionRepository repository;

    public ExportAnalyticsUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public String execute() {
        List<Transaction> transactions = repository.findAll();
        
        double total = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        StringBuilder csvBuilder = new StringBuilder();
        // Cabeçalho enriquecido
        csvBuilder.append("ID,Description,Category,Amount_Cents,Percentage_Of_Total\n");

        for (Transaction t : transactions) {
            double percentage = total > 0 ? (t.getAmount() / total) * 100 : 0.0;
            
            // Tratamento defensivo caso o ID ou a Categoria sejam nulos
            String idVal = t.getId() != null ? t.getId().toString() : "N/A";
            String categoryVal = t.getCategory() != null ? t.getCategory().name() : "NONE";
            
            csvBuilder.append(String.format("%s,%s,%s,%d,%.2f%%\n",
                    idVal,
                    t.getDescription(),
                    categoryVal,
                    t.getAmount(),
                    percentage));
        }

        return csvBuilder.toString();
    }
}