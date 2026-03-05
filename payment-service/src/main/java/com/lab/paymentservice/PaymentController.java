package com.lab.paymentservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final List<Map<String, Object>> payments = new ArrayList<>();
    private int idCounter = 1;

    // GET /payments — return all payments
    @GetMapping
    public List<Map<String, Object>> getPayments() {
        return payments;
    }

    // POST /payments/process — process a payment
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> payment) {
        Map<String, Object> newPayment = new HashMap<>(payment);
        newPayment.put("id", idCounter++);
        newPayment.put("status", "SUCCESS");
        payments.add(newPayment);
        return ResponseEntity.status(201).body(newPayment);
    }

    // GET /payments/{id} — get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPayment(@PathVariable int id) {
        return payments.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
