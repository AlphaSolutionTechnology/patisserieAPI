package com.alphasolutions.patisserie.pojo;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade; // Geralmente a cidade
    private String uf;         // Sigla do estado
    private String estado;     // Nome completo do estado
    private String regiao;     // Região do Brasil
    private String ibge;       // Código IBGE do município
    private String gia;        // Código GIA
    private String ddd;        // Código de Discagem Direta
    private String siafi;      // Código SIAFI

}