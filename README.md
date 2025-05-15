# 📞 Sustav za Upravljanje Korisničkom Podrškom (Customer Support Ticketing System)

Java aplikacija za praćenje korisničkih zahtjeva i tiketa kroz poslovni radni tok.

---

## 📝 Opis

Ovaj sustav omogućuje upravljanje korisničkim upitima, dodjelu prioriteta te automatsko raspoređivanje tiketa agentima podrške. Namijenjen je organizacijama koje žele bolju kontrolu nad korisničkom podrškom.

---

## 🔧 Funkcionalnosti

- ✅ Slanje i upravljanje korisničkim tiketima
- 📊 Praćenje statusa tiketa (otvoren, u radu, riješen)
- 🚨 Postavljanje razina prioriteta
- 👥 Upravljanje timom podrške
- ⚙️ Automatska dodjela tiketa agentima putem niti (threads)
- 🧾 Baza korisničkih profila i zapisa rješenja

---

## 🗄️ Baza podataka

Aplikacija koristi **H2 bazu podataka**, koju je potrebno pokrenuti prije pokretanja same aplikacije.

**Podaci za pristup bazi:**

- **Naziv baze:** `proba`
- **Lokacija:** `baza_stvari/`
- **Postavke za spajanje (nalaze se u `database.properties`):**

```properties
databaseUrl=jdbc:h2:tcp://localhost/~/proba
username=fran
password=fran
