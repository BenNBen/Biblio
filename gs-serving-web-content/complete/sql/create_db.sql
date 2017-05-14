create database biblio;
use biblio;
create table websites (article text, authorFN text, authorLN text, website text, publisher text, url text, publishDate text, accessDate text);

create table lectures (presentation text, speakerFN text, speakerLN text, type text, event text,city text, location text, speechDate text);

create table journals (article text, authorFN text, authorLN text, volume text, journal text, issue text, year text, pageStart text, pageEnd text, dBase text, accessDate text);

create table films (title text, directorFN text, directorLN text,actors text, studio text, medium text, year text);

create table books (title text, authorFN text, authorLN text, volume text, publisher text, edition text, year text, city text);
