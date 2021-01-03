/*
 * @Author: cristianmarint
 * @Date: 3/1/21 10:28
 */

/*
 * @Author: cristianmarint
 * @Date: 9/12/20 8:51
 */

package com.api.clio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Builder.Default
    private Timestamp createdAt= Timestamp.from(Instant.now());
}
