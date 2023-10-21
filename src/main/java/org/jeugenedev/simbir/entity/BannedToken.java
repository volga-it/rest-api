package org.jeugenedev.simbir.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banned_tokens")
public class BannedToken {
    @Id
    @Column(name = "token_id")
    private UUID id;
    @Column(name = "token_base64_payload")
    private String tokenBase64Payload;
    private Timestamp time;
}
