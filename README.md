
### Projet d’algorithmes sur les graphes

## Les réseaux sociaux

# 1 Contexte
Les réseaux sociaux en ligne ont connu un essor spectaculaire ces dernières
années, et les entreprises se sont appropriées ce concepts pour des applica-
tions plus ciblées comme le travail collaboratif, la recherche de compétences
ou encore le recrutement.
Un réseau social peut être déﬁni comme un ensemble d’entités sociales
connectées entre elles en fonction d’intérêts communs. Ces relations peuvent
être uniques (i.e. ami de) ou multi dimensionnelles (client, collègue, relation
spatiale, etc.). Elles peuvent également être homogènes (individus du même
type) ou hétérogènes, symétriques ou non. Les réseaux sociaux d’entreprises
sont caractérisés par des relations multiples et un contenu riche. Les graphes
constituent une représentation adaptée pour modéliser l’information ou les
interactions sociales. L’analyse des réseaux sociaux consiste à comprendre
et interpréter le comportement d’un réseau. Cette analyse peut également
fournir des informations sur la manière dont les communautés se forment
et interagissent. Les réseaux sociaux ont été étudiés d’un point de vue
mathématique et statistique, mais aussi en informatique pour les aspects
recherche, navigation et visualisation sociale.
Il y a donc un besoin important de collecter les données pour les représenter
par un graphe de données sémantiquement riche, les analyser pour trouver
des relations et groupes cachés, faire de la prédiction de liens, caractériser
le réseau et ses principaux acteurs, permettre une recherche sémantique et
par facettes, établir un réseau de conﬁance.
Nous proposons pour ce projet de réaliser une étude (simpliﬁée) d’un
réseau social (simpliﬁé) de type Facebook. Pour mettre en place cette étude,
nous utiliserons quelques notions de théorie des graphes qui vont nous 
permettre de représenter ces réseaux et de les analyser eﬃcacement.

#2 Modélisation du graphe
On considère un réseau social. Les utilisateurs de ce réseau créent des
comptes de deux types : Utilisateur, Page. Les comptes sont représentés
par des sommets dans ce graphe. Chaque compte de type Utilisateur peut
suivre d’autres comptes de type Utilisateur pour voir leurs notiﬁcations.
Chaque compte de type Utilisateur peut aimer des comptes de type Page et
ainsi devenir capable d’en consulter le contenu. En revanche, les comptes
de type Page n’ont aucune action possible. La relation x ”suit” y, avec x
et y des comptes Utilisateur est représentée par un arc du sommet x vers le
sommet y dans le graphe. La relation x aime y , avec x un compte 
Utilisateur et y un compte Page est représentée par un arc du sommet x vers le
sommet y dans le graphe.

# Références

[1]Sciences et Avenir, ”Facebook envoie les citoyens aux urnes”, Septembre
2012,


```
https://www.sciencesetavenir.fr/fondamental/facebook-envoie-les-
citoyens-aux-urnes 22982
```
[2]Wikipedia, ”Etude du petit monde”,
```
http://fr.wikipedia.org/wiki/Etudedupetitmonde
```


