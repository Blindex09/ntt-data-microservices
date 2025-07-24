#!/usr/bin/env python3
import os
import sys

# Lista de arquivos pom.xml para corrigir
pom_files = [
    r"C:\Users\olive\Documents\GitHub\ntt-data-microservices\pom.xml",
    r"C:\Users\olive\Documents\GitHub\ntt-data-microservices\eureka-server\pom.xml", 
    r"C:\Users\olive\Documents\GitHub\ntt-data-microservices\api-gateway\pom.xml",
    r"C:\Users\olive\Documents\GitHub\ntt-data-microservices\product-service\pom.xml",
    r"C:\Users\olive\Documents\GitHub\ntt-data-microservices\order-service\pom.xml"
]

for pom_file in pom_files:
    print(f"Corrigindo {pom_file}")
    
    # Ler o arquivo
    with open(pom_file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Substituir as tags malformadas
    new_content = content.replace('<n>', '<' + 'name' + '>')
    new_content = new_content.replace('</n>', '</' + 'name' + '>')
    
    # Escrever de volta
    with open(pom_file, 'w', encoding='utf-8') as f:
        f.write(new_content)
    
    print(f"Arquivo {pom_file} corrigido!")

print("Todos os arquivos POM corrigidos!")