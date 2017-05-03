create database biblio;
use biblio;
create table website (authorFN text, authorLN text, article text, website text, publisher text, url text, pDate text, aDate text);
create table lecture (speakerFN text, speakerLN text, presentation text, type text, city text, event text, location text, date text);
create table journal (authorFN text, authorLN text, article text, volume text, journal text, issue text, year text, start text, end text, database text, date text);
create table film (directorFN text, directorLN text, film text, studio text, medium text, year text);
create table book (authorFN text, authorLN text, book text, volume text, publisher text, edition text, pYear text, pCity text);
