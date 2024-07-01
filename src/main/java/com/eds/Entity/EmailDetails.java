package com.eds.Entity;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
    private String to;
    private String from;
    private String sub;
    private String body;
}
