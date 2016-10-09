## Synopsis

It is a set of some classes from the course "Information Retrieval" based on the book "An Introduction to Information Retrieval" by Christopher D. Manning and others.

## Content
Now it has following retrieval models:
* Boolean retrieval - supports boolean operations (AND, OR) and parentheses - see class BooleanRetrieavalApp, BooleanRetrieval and tests
* Biword retrieval - see class BiwordRetrieaval and tests
* Free text retrieval - see class FreeTextRetrival and FreeTextRetrivalApp.

## Datasets
You can find some datasets to play with in general/data.

## Preprocessing

### Token filtering
* NumberFilter - eliminates numbers - see NumberFilter

### Token processing
* Normalizer - see NormilizerTokenProcessor
* Soundex - see Soundex and SoundexTokenProcessor
* Porter's stemmer - see Stemmer and StemmerTokenProcessor

