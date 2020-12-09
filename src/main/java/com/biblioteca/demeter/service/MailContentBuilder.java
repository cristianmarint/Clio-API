/*
 * @Author: cristianmarint
 * @Date: 9/12/20 10:05
 */

package com.biblioteca.demeter.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MailContentBuilder {
    private TemplateEngine templateEngine;

    public String build(String message){
        Context context = new Context();
        context.setVariable("message",message);
        return templateEngine.process("mailTemplate",context);
    }
}
