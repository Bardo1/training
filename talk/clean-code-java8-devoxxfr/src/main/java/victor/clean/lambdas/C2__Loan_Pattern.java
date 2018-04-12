package victor.clean.lambdas;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;


interface OrderRepo extends JpaRepository<Order, Long> { // J'aime Spring Data!
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}

class FileExporter {
	private OrderRepo repo;
		
	private String toExportLine(Order order) {
		return "Order " + order.getId() + "\n";
	}
	
	public File exportFile(String fileName) {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			writer.write("Header\n");
			repo.findByActiveTrue()
				.map(this::toExportLine)
				.forEach(writer::write);
			log.debug("Generation done");
			return file;
		} catch (Exception e) {
			log.debug("Coucou!", e); // TERREUR !
			// TODO send email notification
			throw e;
		}
	}
}