package br.com.zup.edu.library.opensessioninview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OpenSessionInViewController {
    @Autowired
    private BookRepository repository;

    @Transactional
    @GetMapping("/transactional-endpoint-with-data-access")
    public ResponseEntity<?> endPoint1() {

        List<Book> all = repository.findAll();

        return ResponseEntity.ok(all);
    }

    @GetMapping("/not-transactional-endpoint-with-data-access")
    public ResponseEntity<?> notTransactionalEndPoint1() {

        List<Book> all = repository.findAll();

        return ResponseEntity.ok(all);
    }


    @Transactional
    @GetMapping("/transactional-endpoint-without-data-access")
    public ResponseEntity<?> endPoint2()  {
        System.out.println("Aqui não acessa ao RDBMS");

        return ResponseEntity.ok().build();
    }

    @Transactional(readOnly = true)
    @GetMapping("/transactional-readonly-endpoint-without-data-access")
    public ResponseEntity<?> redendPoint2()  {
        System.out.println("Aqui não acessa ao RDBMS");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/not-transactional-endpoint-without-data-access")
    public ResponseEntity<?> notEndPoint2()  {
        System.out.println("Aqui não acessa ao RDBMS");

        return ResponseEntity.ok().build();
    }
}
