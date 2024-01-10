Grigoreanu Andreea-Georgiana - 323 CB - Tema 2 - OOP

<br><br>
Clasa Student: <br>
- primeste numele si media studentului si adauga intr-o colectie de tip arrayList cursurile pe care le prefera;
- functia adaugaPreferinta(String... numeCursuriPreferate) primeste un numar variabil de parametrii (nestiind cate cursuri prefera fiecare student) si  are rolul de a adauga preferinte de cursuri in lista de preferinte a studentului. aceasta utilizeaza metoda addall pentru a adauga toate elementele dintr-un array de string-uri in lista de preferinte a studentului. M-am gandit ca e mai potrivit sa folosesc un parametru variabil, decat un array cu o dimensiune fixa, avand in vedere ca numaril de cursuri preferate difera de la student la student.

Clasele Licenta si Master extind clasa Student. Practic acestea mostenesc toate proprietatile clasei Student.

Clasa Curs: <br>
- are rolul de a gestiona inscrieri de studentilor la cursuri;
- este parametrizata generic cu elemente de tip Student, care permite folosirea tipurilor de studenti de licenta sau master;
- pentru a inrola studentii la curs folosesc tot un arrayList;
- astfel folosesc functia “inrolareStudent” care primeste ca parametrii stundetul si tipul de program la care este (licenta sau master), pe urma in functie de locurile disponibile se adauga la cursrul respectiv. am mai adaugat o verificare numita “ultimaMedie” care verifica daca studentii care vor la un anumit curs au aceiasi medie si pentru a putea fi si ei inrolati la curs chiar daca se depaseste numarul limita de locuri la curs.

Clasa Secretariat: <br>
- are rolul de a gestiona studentii si cursurile;
- in interiorul clasei am folosit doua elemente diferite din Java Collection. am folosit un arratList care stocheaza toate obiectele Curs;
- am mai folosit si de HashMap pentru a stoca informatiile despre studenti. Astfel, cheia este chiar numele studentului, iar valorile sunt obiecte de tip Student. M-am gandit sa folosesc HashMap-ul pentru ca am zis ca este mai simplu sa caut studentii dupa nume (cheie);
- functia “adaugaStudent” verifica programul de studiu si adauga studentii, iar pe urma ii adauga in HashMap;
- in “posteazaMediile” am pus elemntele din HashMap intr-o noua lista pentru a nu modifica ordinea initiala dupa ce aplic sortarea descrectaore dupa medii, iar in caz de egalitate, alfabetica dupa nume. Pe urma scriu datele in fisier;
- functia “contestatie” actualizeaza media studentului;
- functia “adaugaPreferinte”, primeste numele studentului si o serie de nume de cursuri preferate pe care le adauga in lista de preferinte a studentului;
- functia “repartizare”, repartizeaza studentii la cursuri, respectand preferintele. Acesta se opreste daca reuseste sa inroleze studentul si trece la urmatorul.

Clasa Main: <br>
- in main mentionez fisierele de citire si scriere si definesc comenzile cerute. Toate functiile din main apeleaza metode din clasa Secretariat;
- tot in main tratez si exceptiile, pentru care am creat doua clase numite StudentDuplicatException si StudentNotFoundException.
