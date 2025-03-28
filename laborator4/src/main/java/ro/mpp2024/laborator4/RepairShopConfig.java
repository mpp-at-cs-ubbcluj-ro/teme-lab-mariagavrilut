package ro.mpp2024.laborator4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.mpp2024.laborator4.repository.ComputerRepairRequestRepository;
import ro.mpp2024.laborator4.repository.ComputerRepairedFormRepository;
import ro.mpp2024.laborator4.repository.file.ComputerRepairRequestFileRepository;
import ro.mpp2024.laborator4.repository.file.ComputerRepairedFormFileRepository;
import ro.mpp2024.laborator4.repository.jdbc.ComputerRepairRequestJdbcRepository;
import ro.mpp2024.laborator4.repository.jdbc.ComputerRepairedFormJdbcRepository;
import ro.mpp2024.laborator4.services.ComputerRepairServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties props = new Properties();
        try{
            System.out.println("searching bd.config in directory"+((new File(".")).getAbsolutePath()));
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.err.println("config not found" + e);
        }
        return props;
    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){
       
        return new ComputerRepairRequestJdbcRepository(getProps());
        //return new ComputerRepairRequestFileRepository("ComputerRequests.txt");
    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
       return new ComputerRepairedFormJdbcRepository(getProps());
       //return new ComputerRepairedFormFileRepository("RepairedForms.txt", requestsRepo());


    }

    @Bean
    ComputerRepairServices services(){
       
        return new ComputerRepairServices(requestsRepo(), formsRepo());
    }

}
