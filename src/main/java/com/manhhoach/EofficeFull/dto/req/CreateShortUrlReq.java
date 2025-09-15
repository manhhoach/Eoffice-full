package com.manhhoach.EofficeFull.dto.req;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateShortUrlReq {
    private String longUrl;
    private String alias;
    private LocalDateTime expirationDate;
}
