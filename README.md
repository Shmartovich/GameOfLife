# Conways Spiel des Lebens

## Beschreibung

Eine Java-Implementierung von **Conways Spiel des Lebens** mit der Konsoleausgabe und auch mit GUI. In
src/main/java/Main.java lässt sich unnötige auskommentieren.

Das Spielfeld besteht aus einem zweidimensionalen boolean Array, da das Feld aus Zellen besteht und jede Zelle hat nur 2
Zustände.

Dabei bedeutet:

```
true  = lebendig
false = tot
```

## Regeln

Für jede Zelle werden die 8 Nachbarn überprüft.

Die Regeln sind:

* weniger als 2 lebende Nachbarn → Zelle stirbt
* 2 oder 3 lebende Nachbarn → Zelle bleibt lebendig
* mehr als 3 lebende Nachbarn → Zelle stirbt
* tote Zelle mit genau 3 lebenden Nachbarn → wird lebendig

## Neue Generation berechnen

### Problem - Aktualisierung des Spielfelds

Wenn man beim Durchlaufen des Arrays die Zellen sofort im ursprünglichen Array verändert, entstand ein Fehler: Später
berechnete Zellen sehen dann teilweise schon die neue Generation und teilweise noch die alte. 2 Generationen vermischten
sich.

### Lösung

Deshalb benutze ich für die nächste Generation ein zweites Array und ersetze das alte Spielfeld erst, nachdem alle
Zellen berechnet wurden.

## Nachbarn zählen

Am Anfang wollte ich die Nachbarn mit mehreren `if`-Abfragen überprüfen, was aber nicht flexibel wäre.

Deshalb benutze ich relative Positionen für die 8 Richtungen, wo Werte von links zu `row` und von rechts zu `col`
addiert werden. Die sind in einem 2D Array gespeichert für flexibles Durchlaufen (falls Algorithmus für Nachbarnzählen
ändern soll)

```
                {-1, 0},
                {-1, -1},
                {-1, 1},

                {0, -1},
                {0, 1},

                {1, 0},
                {1, -1},
                {1, 1}
```

## Spielfeldrand

Bei der Umsetzung musste ich entscheiden, welche Art von Spielfeld ich verwenden möchte.

Dabei gab es drei Möglichkeiten:

* **Toroides Spielfeld**
  Die gegenüberliegenden Seiten sind miteinander verbunden. Eine Zelle am linken Rand kann zum Beispiel eine Zelle am
  rechten Rand als Nachbarn haben.

* **Unendliches Spielfeld**
  Theoretisch kann sich das Spielfeld dabei unbegrenzt vergrößern. Deutlich komplizierter zu implementieren.

* **Begrenztes Spielfeld mit festen Rändern**
  Das Spielfeld hat eine feste Größe. Positionen außerhalb des Arrays werden nicht als Nachbarn gezählt.

Ich habe mich für die dritte Variante entschieden, weil sie für meine Implementierung am einfachsten und
verständlichsten ist.

Dadurch kann ich weiterhin ein normales `boolean[][]` verwenden und muss weder das Spielfeld dynamisch vergrößern noch
die gegenüberliegenden Ränder miteinander verbinden.

## Randomisierung

Am Anfang wollte ich für jede lebende Zelle einfach zufällige row- und col-Werte erzeugen, aber dabei könnten dieselben
Positionen mehrfach ausgewählt werden.

Deshalb behandle ich das 2D Spielfeld zuerst wie eine eindimensionale Liste von Positionen.
Dadurch wird jede ausgewählte Position nur einmal verwendet.

## Struktur

```
Main

entities/
    GameField

utilities/
    Calculator
    Drawer

gui/
    GameOfLifeFrame
    GamePanel
```

### `GameField`

`GameField` erstellt und speichert das Spielfeld.

### `Calculator`

`Calculator` berechnet aus dem aktuellen Spielfeld die nächste Generation.

### `Drawer`

`Drawer` ist für die Darstellung des Spielfelds in der Konsole zuständig.

### `GameOfLifeFrame`

`GameOfLifeFrame` gehört zur grafischen Benutzeroberfläche und enthält das Hauptfenster der Anwendung.

### `GamePanel`

`GamePanel` ist der Bereich der GUI, in dem das Spielfeld grafisch dargestellt wird.

## Darstellung

Für das Projekt gibt es zwei Möglichkeiten, das Spielfeld darzustellen:

* Ausgabe in der Konsole
* grafische Oberfläche mit Java Swing

### Konsolenausgabe

In der Konsole wird eine lebende Zelle als

```
■
```

und eine tote Zelle als

```
·
```

dargestellt.

Für die Ausgabe wird ein `StringBuilder` verwendet.

Die einzelnen Zeichen des Spielfelds werden zuerst zu einem kompletten Frame zusammengesetzt. Erst danach wird der
gesamte Frame auf einmal in der Konsole ausgegeben.

Dadurch müssen nicht für jede einzelne Zelle separate `System.out.print()`-Aufrufe ausgeführt werden. Das macht die
Ausgabe etwas effizienter und reduziert außerdem unnötiges Flackern bei der Animation.

### GUI

Zusätzlich gibt es eine einfache grafische Benutzeroberfläche mit Java Swing.

Dafür befinden sich die Klassen `GameOfLifeFrame` und `GamePanel` im Package `gui`.

So ist die Darstellung von der eigentlichen Spiellogik getrennt und lassen sich besser parallel entwickeln.

## Tests

Die Spiellogik wird mit **JUnit 6** getestet.

Dabei werden folgende Fälle überprüft:

* **Block:** Ein stabiles Muster bleibt unverändert.
* **Blinker:** Das Muster wechselt zwischen horizontaler und vertikaler Form.
* **Unterbevölkerung:** Eine lebende Zelle mit zu wenigen Nachbarn stirbt.
* **Überbevölkerung:** Eine lebende Zelle mit zu vielen Nachbarn stirbt.
* **Geburt:** Eine tote Zelle mit genau drei lebenden Nachbarn wird lebendig.
* **Spielfeldrand:** Auch Zellen am Rand werden korrekt berechnet.

Block und Blinker werden getestet, da sie unterschiedliche Situationen abdecken: Der Block überprüft ein
unveränderliches Muster, während der Blinker die korrekte Veränderung über mehrere Generationen testet.