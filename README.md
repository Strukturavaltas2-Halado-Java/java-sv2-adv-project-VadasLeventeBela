# Vizsgaremek

## Leírás

Ebben a feladatban egy könyvtár könyv-bérlő rendszerét fogom majd elkészíteni a saját elképzelésem alapján. Ezt a feladatot azért válsztottam, mert próbáltam elképzelni egy olyan helyzetet amely valósághű, de jól láthtóak a nögötte rejlő logikai lépések. A program három entitást és négy táblát fog tartalmazni. Két entitás a bérlő és a könyv ami 1-n kapcsolattal rendelkezik (az azonos könyveknél az id mint sorozatszám tesz különbséget) ez két tábla, ezek kapcsoló táblája a harmadik, és a negydik tábla a könyvtár nyilvántartása lessz. A könyvtárból lehet könyveket ki venni amíg van készlet és lehet vissza hozni. A vissza adás határidőre megy és ha ezt túllépi akkor kap egy figyelmeztetést. Addott figyelmeztetés után ideiglenes felfüggesztés jár a könyvbérlésre.

---

## Felépítés

### Person

* id
* name (nen üres)
* date_of_birth (nem üres,dátum)
* books (nem null)
* warnings (0 vagy pozitív)
* suspension_date (dátum)

Végpontok:


| HTTP metódus | Végpont                 | Leírás                                                                 |
| ------------ | ----------------------- | ---------------------------------------------------------------------- |
| GET          | `"/api/people/find-all-people"`       | lekérdezi az összes személyt                             |
| GET          | `"/api/people/find-person/{id}"`      | lekérdez id alapján egy személyt                         |
| POST         | `"/api/people/create-new-person"`     | létrehoz egy új személyt                                 |
| PUT          | `"/api/people/update-warnings"`       | frissíti a figyelmeztetéseket                            |
| DELETE       | `"/api/people/remove-person/{id}"`    | törli a kiválsztott személyt                             |
| DELETE       | `"/api/people/remove-people"`         | törli az összes személyt                                 |

---

### Book

* id
* title (nem üres)
* description (nem üres)
* current_holder (person)
* time_of_return (dátum)
* checked

| HTTP metódus | Végpont                 | Leírás                                                                 |
| ------------ | ----------------------- | ---------------------------------------------------------------------- |
| GET          | `"/api/books/find-all-books"`       | lekérdezi az összes könyvet                                |
| GET          | `"/api/books/find-book/{id}"`       | lekérdez id alapján egy könyvet                            |
| POST         | `"/api/books/create-new-book"`      | létrehoz egy új könyvet                                    |
| PUT          | `"/api/books/update-time-of-return"`  | frisíti a visszatérítés dátumát                          |
| DELETE       | `"/api/books/remove-book/{id}"`     | törli a kiválsztott könyvet                                |
| DELETE       | `"/api/books/remove-books"`         | törli az összes könyvet                                    |

---

### Library

* id
* book_title (nem üres)
* amount (nem üres,0 vagy nagyobb)

| HTTP metódus | Végpont                 | Leírás                                                                 |
| ------------ | ----------------------- | ---------------------------------------------------------------------- |
| GET          | `"/api/library/find-all-books"`       | lekérdezi az összes könyvtípust                          |
| GET          | `"/api/library/find-book/{id}"`       | lekérdez id alapján egy könyvtípust                      |
| POST         | `"/api/library/create-new-book"`      | létrehoz egy új könyvtípust                              |
| PUT          | `"/api/library/rent-new-book"`        | új könyvet ad a személyhez                               |
| PUT          | `"/api/library/return-book"`          | vissza rakja a könyvet a könyvtárba                      |
| DELETE       | `"/api/library/remove-book/{id}"`     | törli a kiválsztott könyvtípust                          |
| DELETE       | `"/api/library/remove-books"`         | törli az összes könyvtípust                              |

---