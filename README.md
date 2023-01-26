# ISA Godina 2022-2023 FTN E2 PRNII  
## Centralizovani informacioni sistem centra za transfuziju krvi   
Projekat iz predmeta Internet Softverske Arhitekture  
Fakultet tehničkih nauka  
Računarstvo i automatika  
Primenjene računarske nauke i informatika  

* Student 1: RA 145/2019 - Jovan Srdanov  
* Student 2: RA 152/2019 - Strahinja Eraković   
* Student 3: RA 143/2019 - Stefan Apostolović  
* Student 4: RA 149/2019 - Aleksandar Stojanović  

### Tehnologije:   
* Front-end: Angular  (npm)  
* Back-end: Java Spring Boot (Maven)   
* Database: PostgreSQL  
* Message Queue: RabbitMQ

###  Alati korišćeni prilikom izrade aplikacije:   
* Visual Studio Code - razvijanje frontend aplikacije  
* IntelliJ IDEA - razvijanje backend aplikacije  
* DBeaver - rad sa bazom podataka  
* pgAdmin - rad sa bazom podataka  

### Pokretanje aplikacije:  
* [Preduslov za Angular](https://angular.io/guide/setup-local#prerequisites)  
* Pokretanje Angular aplikacije: preko komandne linije pozicionirati se u folder ISA_2022_2023/FrontEnd/ISA/ i kucati naredbu: npm install --force, zatim ng serve  

* [Instalacija i pokretanje RabbitMQ](https://www.youtube.com/watch?v=V9DWKbalbWQ) 

* Potrebno je instalirati i PostgreSql, napraviti dve baze podataka : bloodbank i gps  
postgresql://localhost:5432/bloodbank  
postgresql://localhost:5432/gps  
Za obe:   
username=postgres    
password=password  
Obe baze podataka rade po principu create-drop  

* Pokretanje Java Spring Boot aplikacije (IntelliJ IDEA):  
Projects -> Open -> (3 projekta trebaju da se pokrenu: ExternalHospital, HelicopterGPS, BackEnd/ISA_2022)  
instalirati sve dependency-je iz pom.xml  
Run -> Run "Naziv projekta"  

* Pokretanje Spring aplikacije (Eclipse) 
importovati projekat u workspace: Import -> Maven -> Existing Maven Project  
instalirati sve dependency-je iz pom.xml  
desni klik na projekat -> Run as -> Java Application / Spring Boot app (ako je instaliran STS plugin sa Eclipse marketplace)  

### Logovanje na aplikacij:  
* Svima je isti password i to je password (sifra je hesirana pa se ne vidi tacna vrednost u bazi)  
U datoteci data.sql se nalaze emailovi od korisnika i koja su rola, to je tabela Account, role su enumerisanne na sledeci nacin  
1: BLOOD_DONOR  
2: STAFF  
3: SYSTEM_ADMIN  
