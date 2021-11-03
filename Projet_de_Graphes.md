

**L3 INFO**

**Projet d’algorithmes sur les graphes**

**Les r´esaux sociaux**

**1 Contexte**

Les r´eseaux sociaux en ligne ont connu un essor spectaculaire ces derni`eres

ann´ees, et les entreprises se sont appropri´ees ce concepts pour des applica-

tions plus cibl´ees comme le travail collaboratif, la recherche de comp´etences

ou encore le recrutement.

Un r´eseau social peut ˆetre d´eﬁni comme un ensemble d’entit´es sociales

connect´ees entre elles en fonction d’int´erˆets communs. Ces relations peuvent

ˆetre uniques (i.e. ami de) ou multi dimensionnelles (client, coll`egue, relation

spatiale, etc.). Elles peuvent ´egalement ˆetre homog`enes (individus du mˆeme

type) ou h´et´erog`enes, sym´etriques ou non. Les r´eseaux sociaux d’entreprises

sont caract´eris´es par des relations multiples et un contenu riche. Les graphes

constituent une repr´esentation adapt´ee pour mod´eliser l’information ou les

interactions sociales. L’analyse des r´eseaux sociaux consiste `a comprendre

et interpr´eter le comportement d’un r´eseau. Cette analyse peut ´egalement

fournir des informations sur la mani`ere dont les communaut´es se forment

et interagissent. Les r´eseaux sociaux ont ´e t´e ´etudi´es d’un point de vue

math´ematique et statistique, mais aussi en informatique pour les aspects

recherche, navigation et visualisation sociale.

Il y a donc un besoin important de collecter les donn´ees pour les repr´esenter

par un graphe de donn´ees s´emantiquement riche, les analyser pour trouver

des relations et groupes cach´es, faire de la pr´ediction de liens, caract´eriser

le r´eseau et ses principaux acteurs, permettre une recherche s´emantique et

par facettes, ´etablir un r´eseau de conﬁance.

Nous proposons pour ce projet de r´ealiser une ´etude (simpliﬁ´ee) d’un

r´eseau social (simpliﬁ´e) de type Facebook. Pour mettre en place cette ´etude,

nous utiliserons quelques notions de th´eorie des graphes qui vont nous per-

mettre de repr´esenter ces r´eseaux et de les analyser eﬃcacement.

1





**2 Mod´elisation du graphe**

On consid`ere un r´eseau social. Les utilisateurs de ce r´eseau cr´eent des

comptes de deux types : Utilisateur, Page. Les comptes sont repr´esent´es

par des sommets dans ce graphe. Chaque compte de type Utilisateur peut

suivre d’autres comptes de type Utilisateur pour voir leurs notiﬁcations.

Chaque compte de type Utilisateur peut aimer des comptes de type Page et

ainsi devenir capable d’en consulter le contenu. En revanche, les comptes

de type Page n’ont aucune action possible. La relation x ”suit” y, avec x

et y des comptes Utilisateur est repr´esent´ee par un arc du sommet x vers le

sommet y dans le graphe. La relation x aime y , avec x un compte Utilisa-

teur et y un compte Page est repr´esent´ee par un arc du sommet x vers le

sommet y dans le graphe.

**3 Gestion du graphe**

Les comptes Utilisateur sont identiﬁ´es par un nom et ont des informations

suppl´ementaires comme le pr´enom et l’ˆage. Les comptes Page sont ´egalement

identiﬁ´es par un nom et ont une liste d’administrateurs de type Utilisateur.

Aﬁn de g´erer le graphe mod´elisant le r´eseau, il vous est demand´e de :

• cr´eer une classe abstraite Sommet, d´eﬁnir un constructeur ainsi que des

accesseurs publics. Chaque Sommet contiendra la liste de ses voisins

sortants;

• ´ecrire les classes Utilisateur et Page qui h´eritent de la classe Sommet;

• coder la classe Graphe qui devra repr´esenter un graphe sous forme de

liste d’adjacence et donc contenir un ensemble de Sommet.

**4 Op´erations de base sur le graphe**

Pour commencer `a ´etudier les propri´et´es du graphe d’un r´eseau social, il

faut tout d’abord ˆetre capable d’en explorer le contenu facilement et de

disposer de quelques mesures simples mais tr`es utilis´ees. Pour cela, vous

devez impl´ementer les fonctionnalit´es suivantes :

• connaˆıtre le nombre de sommets, d’arcs;

• obtenir l’ensemble des sommets;

2





• obtenir l’ensemble des sommets tri´e par nom;

• obtenir l’ensemble des sommets tri´e par degr´e sortant;

• obtenir l’ensemble des arcs;

• ajouter/Supprimer un sommet (et les arcs qui y sont li´es);

• ajouter/Supprimer un arc;

• obtenir les informations sur un sommet via son nom;

• connaˆıtre le nombre de comptes de type Page, Utilisateur;

• Connaˆıtre l’ˆage moyen des Utilisateur;

• Connaˆıtre tous les comptes Utilisateur qui sont des administrateurs de

Page;

• ´ecrire le graphe dans un ﬁchier sous forme de liste d’adjacence;

• lire un graphe dans un ﬁchier sous forme de liste d’adjacence.

Une interface devra ˆetre propos´ee dans votre application pour qu’un utilisa-

teur puisse appeler toutes ces fonctionnalit´es, en visualiser les r´esultats et

ainsi commencer `a fouiller les donn´ees de son graphe.

**5 Qui est le plus inﬂuent ?**

De nombreuses m´ethodes sont utilis´ees pour mesurer l’inﬂuence ou la per-

tinence d’un utilisateur sur un r´eseau social, notamment sur Twitter. Une

m´ethode possible est d’appliquer un algorithme de PageRank, ´egalement

utilis´e par Google pour r´ealiser le classement des r´esultats sur son moteur

de recherche. L’intuition de cet algorithme est celle-ci : Plus le nombre

de comptes qui me suivent est ´elev´e, plus mon compte est inﬂuent et per-

tinent; Plus les comptes qui me suivent sont inﬂuents et pertinents, plus

mon compte est inﬂuent et pertinent;

Aﬁn de pouvoir d´eterminer les comptes Utilisateur et Page qui sont

les plus inﬂuents au sein du graphe, cet algorithme devra ˆetre impl´ement´e

dans votre application. Par ailleurs, l’interface utilisateur sera enrichie de

la possibilit´e de lancer cet algorithme et d’en visualiser les r´esultats.

3





**6 Des r´eseaux Small world**

En 1967, Stanley Milgram, sociologue, d´eclarait que que chaque ˆetre hu-

main sur terre ´etait reli´e `a n’importe quel autre ˆetre humain via quelques

(six) degr´es de connaissance seulement.Cette aﬃrmation est ´egalement con-

nue comme le paradoxe de Milgram [**?**]. Pour v´eriﬁer cette hypoth`ese, vous

impl´ementerez l’algorithme de la section **??**. Il prend en param`etre un som-

met du graphe (qu’on appelle source) et permet de calculer la plus petite dis-

tance entre ce sommet source et chacun des autres sommets du graphe. En

ex´ecutant cet algorithme avec pour source chacun des sommets du graphe,

on peut connaˆıtre la plus petite distance entre chaque paire de sommets du

graphe.

**7 Algorithmes**

**7.1 Notations**

On appelle degr´e entrant (resp. sortant) d’un sommet v ∈ V le nombre

−

\+

d’arcs dont l’extr´emit´e (resp. l’origine) est v. On le note d (v) (resp. d (v)).

On dit que v ∈ V est un voisin sortant (resp. entrant) de u ∈ V s’il

existe un arc (u, v) (resp. (v, u)) ∈ A. Pour v ∈ V , nous d´eﬁnissons N (v)

\+

−

(resp. N (v)) comme l’ensemble des voisins sortants (resp. entrants) de v.

**7.2 Calcul du Pagerank**

// Initialisation du PageRank `a 1 pour tous les sommets

Pour tout v ∈ V faire

PR(v) ← 1;

// Calcul du PageRank

i ← 0;

Tant que i ≤ 100 faire

// Pour tous les sommets du graphe

Pour tout v ∈ V faire

// On calcule le PageRank du sommet

∑

0.15

|V |

PR(u)

u∈N−(v) d+(u)

P R(v) ←

i ← i + 1;

\+ 0.85

;

4





**7.3 Plus courtes distances entre la source** s **et les sommets**

**de** V

//Initialisation des distances entre chaque sommet et la source s `a 10000000

∀u ∈ V, dist(u) ← 10000000;

// Initialisation de la distance du sommet source `a 0 dist(s) ← 0;

// P prend l’ensemble des sommets du graphe

P ← V ;

// Calcul de la plus petite distance entre chaque sommet et la source

Tant que P non Vide faire

// On prend le sommet u de P le plus proche de la source

u ← sommet de P avec dist minimale;

Supprimer u de P;

// Pour tous les voisins de ce sommet u

Pour tout v ∈ N (u) faire

alt ← dist(u, s) + 1;

Si alt ≤ dist(v, s) alors

dist(v, s) ← alt;

\+

**8 Modalit´es**

Ce projet doit ˆetre r´ealis´e par groupes d’au plus quatre ´etudiants.

Le langage de programmation devra ˆetre l’un des suivants : C, Python,

Ocaml ou Java.

Le code source, le code ex´ecutable et le rapport devront ˆetre d´epos´es sous

forme d’archive, sur la page Universtice ”Algorithmique3-Graphes” dans

le d´epˆot de votre enseignant(e) de TD, ceci au plus tard **dimanche 12**

**dcembre, `a minuit**.

Le rapport devra comprendre une notice d’utilisation, la description des

structures de donn´ees, les algorithmes principaux ainsi que leur complexit´e,

des choix li´es `a la programmation, des diﬃcult´es rencontr´ees et des jeux

d’essai. Les projets donneront lieu `a une soutenance avec d´emonstration qui

aura lieu entre lundi 13 et vendredi 17 d´ecembre.

**References**

[1] Sciences et Avenir, ”Facebook envoie les citoyens aux urnes”, Septembre

2012,

5





https://www.sciencesetavenir.fr/fondamental/facebook-envoie-les-

citoyens-aux-urnes 22982

[2] Wikipedia, ”Etude du petit monde”,

http://fr.wikipedia.org/wiki/Etude du petit monde

6

