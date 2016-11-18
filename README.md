# IBAN Validierung für GS-Verein
Mit diesem Programm können die IBAN Daten der Vereinsmitglieder validiert werden. Das Programm ist deshalb enstanden, da es in GS-Verein keine Möglichkeit gibt, die IBAN Daten bei Eingabe zu validieren.

So kann man sicherstellen, dass die eingegeben IBAN Daten korrekt sind und wird nicht erst unmittelbar vor dem Beitragseinzug darauf hingewiesen.

# Voraussetzungen
Auf Deinem PC muss Java installiert sein. Um Java zu installieren und für weitere Anweisungen, bitte folgende Webseite besuchen
www.java.com

# Vorgehen
1. Damit das Programm funktioniert, müssen die Mitgliesdaten nach Excel exportiert werden. Die Export-Datei muss mindestens die folgenden Felder enthalten: "Vorname", "Nachname", "IBAN". Die Felder Vor/Nachname müssen deshalb drin sein, damit man anschließend die ungültigen IBANs wieder den Mitgliedern zuordnen kann. Ihr könnt auch andere Felder statt des Names nehmen. 
Wichtig ist nur, dass an 3. Stelle die IBAN steht.
2. Danach öffnet ihr die exportierte Excel-Datei und speichert Sie als "CSV (DOS Format)" ab
3. Ihr ladet euch diese Datei herunter: https://github.com/phreakadelle/gsverein-iban-validator/blob/master/target/gsverein-iban-tool.jar
3. Ihr öffnet eine Kommando-Zeile (Start -> Ausführen -> "cmd") und gebt ein "java -jar gsverein-iban-tool.jar --input C:/Pfad/Zu/Eurer/CSV/Datei.csv --output Ausgabe.csv"
4. Auf der Bildschirmausgabe könnt ihr nun die falschen Daten erkennen.

![Alt text](/src/site/resources/2016-11-18_120801.jpg?raw=true "Beispielausgabe")
