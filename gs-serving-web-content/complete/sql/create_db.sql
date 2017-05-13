create database biblio;
use biblio;
create table website (authorFN text, authorLN text, article text, website text, publisher text, url text, pDate text, aDate text);
create table lecture (speakerFN text, speakerLN text, presentation text, genre text, city text, event text, location text, jDate text);
create table journal (authorFN text, authorLN text, article text, volume text, journal text, issue text, pYear text, jStart text, jEnd text, db text, jDate text);
create table film (directorFN text, directorLN text, film text, studio text, medium text, fYear text);
create table book (authorFN text, authorLN text, book text, volume text, publisher text, edition text, pYear text, pCity text);
