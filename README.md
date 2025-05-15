# 📞 Customer Support Ticketing System

Java aplikacija za upravljanje korisničkom podrškom i tiketima.

---

## 📝 Opis

Ovaj sistem omogućava praćenje korisničkih zahteva kroz poslovni workflow. Pruža podršku za više agenata, prioritizaciju problema i evidenciju korisničke komunikacije.

---

## 🔧 Funkcionalnosti

- ✅ Slanje i upravljanje korisničkim tiketima
- 📊 Praćenje statusa i prioriteta
- 👥 Upravljanje timom za podršku
- ⚙️ Automatsko dodeljivanje tiketa pomoću niti (threads)
- 🧾 Evidencija korisničkih profila i rešenja

---

## 🗄️ Baza podataka

Aplikacija koristi **H2 bazu podataka**, koju je neophodno pokrenuti pre pokretanja aplikacije.

**Detalji baze:**

- **Ime baze:** `proba`
- **Lokacija:** `baza_stvari/`
- **Konekcioni podaci (nalaze se u `database.properties`):**

```properties
databaseUrl=jdbc:h2:tcp://localhost/~/proba
username=fran
password=fran
