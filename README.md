# jMetalMSA: a framework for solving Multiple Sequence Alignment problems with Multi-Objective metaheuristics

jMetalMSA is an Open source software tool aimed at solving multiple sequence alignment (MSA) problems by using multi-objective metaheuristics. It is based on the jMetal multi-objective framework, which is extended with an encoding for representing MSA solutions. 

##Architecture of jMetalMSA

![alt tag](https://github.com/jMetal/jMetalMSA/blob/master/architecture/jmetalmsaarchitecture.png)

The object-oriented architecture of jMetalMSA is shown in Figure above, is composed of four core classes 
(Java interfaces). Three of them (MSAProblem, MSAAlgorithm, and MSASolution) inherits from their
counterparts in [jMetal](https://github.com/jMetal/jMetal) (the inheritance relationships are omitted in the diagram), and there is a class Score to represent a
given MSA scoring function.

## Summary of features

##List of Algorithms
The list of metaheuristics currently available in jMetalMSA include the evolutionary algorithms 
* NSGA-II [1] 
* NSGA-III [2] 
* SMS-EMOA [3]
* SPEA2 [4]
* PAES [5]
* MOEA/D [6]
* MOCell [7]
* GWASF-GA [8].

## Crossover Operator
The crossover operator is the Single-Point Crossover adapted to alignments, randomly selects a position from the parent A
by splitting it into two blocks and the parent B is tailored so that the right piece can be joined to the left piece of 
the first parent (PA1) and vice versa. Selected blocks are crossed between these two parents

## Mutation Operators
The list of mutation operators included in jMetalMSA are:
* Shift-closed gaps: Closed gaps are randomly chosen and shifted to another position.
* Non-gap group splitting: a non-gap group is selected randomly, and it is split into two groups.
* One gap insertion: Inserts a gap in a random position for each sequence.
* Two adjacent gap groups merging: Selects a random group of gaps and merge with its nearest group of gaps.
* Multiple mutation 

## Scores
The scores that are currently available in jMetalMSA are:
* Sum of Pairs
* Weighted Sum of Pairs with Affine Gaps
* Single sTRucture Induced Evaluation (STRIKE).
* Percentage of Totally Conserved Columns.
* Percentage of Non-Gaps
 
## Requirements
To use jMetalMSA the following software packages are required:
* [Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?ssSourceSiteId=otnes)
* [Apache Maven](https://maven.apache.org/)
* [Git](https://git-scm.com/)
* [Strike Contacts Generator](https://github.com/cristianzambrano/strikeContactGenerator)

## Downloading and compiling
To download jMetalMSA just clone the Git repository hosted in GitHub:
```
git clone https://github.com/jMetal/jMetalMSA.git
```
Once cloned, you can compile the software and generate a jar file with the following command:
```
mvn package
```
This sentence will generate a directory called `target` which will contain a file called `jmetalmsa-1.0-SNAPSHOT-jar-with-dependencies.jar`

## Download PDB files and Generation of the Strike Contact Matrix files

jMetalMSA provides a utility to download the PDB structures files from the [Protein Data Bank](http://www.rcsb.org) and generate the Strike Contact Matrix (with the utility [STRIKE Contact Matrix Generator](https://github.com/cristianzambrano/strikeContactGenerator)) automatically. 

To execute this utility and get all the structural information requeried by STRIKE measure, run this command:

````
java -cp target/jmetalmsa-1.0-SNAPSHOT-jar-with-dependencies.jar org.uma.jmetalmsa.strike.GenerateStructuralInformation sequencesFileName outputDirectory pathToStrike_contactgenerator
```

* sequencesFileName: the filename of the sequences dataset (in FASTA Format). The names of the sequences must be correctly defined, because, the four first letters will be the `Sequence Key` or `PDB ID` to search the Structural Information (PDB) into the database of the Protein Data Bank.
* outputDirectory: The Path where all files (PDB's (*.pdb) and Strike Contact Matrix (*.contacts)) will be save.
* pathToStrike_contactgenerator: The full path of the  STRIKE Contact Matrix Generator executable ,for instance: `/home/jMetalMSA/strike/bin/strike_pdbcontactsgenerator`.

The Contacts files for each sequence of the dataset will be created into the outputDirectory. jMetalMSA will read the contact files to evaluate the STRIKE metric of the aligments. 

## Runing jMetalMSA

To execute the MOCell algorithm to align a particular dataset of sequences with three objectives: SOP, TC and Non-Gaps, just run this command:

````
java -cp target/jmetalmsa-1.0-SNAPSHOT-jar-with-dependencies.jar org.uma.jmetalmsa.runner.MOCellRunner sequencesFileName PDB_ContactsDataDirectory listOfPreComputedAlignments NumberOfEvaluations PopulationSize
```
* sequencesFileName: the filename of the sequences dataset (in FASTA Format).
* dataDirectory: The Path that contains the Structural Information files (PDB's (*.pdb) and Strike Contact Matrix (*.contacts)) of the sequences to align and the Pre-Computed alignments to use to generate the Initial population of the algorithm.  
* listOfPreComputedAlignments: A list of filenames of the pre-alignments separated by `-`, only the file names must be defined, because jMetalMSA will be search these files into the `dataDirectory`.
* NumberOfEvaluations: Number of the Maximun Evaluations of the algorithm.
* PopulationSize: Size of the population of the algorithm

To execute the NSGA-II with  three objectives STRIKE, TC and %Non-Gaps (MOSAStrE) to solve a problem in BAliBASE, just run this command:

````
java -cp target/jMetalMSA-1.0-SNAPSHOT-jar-with-dependencies.jar org.uma.jmetalmsa.runner.MOSAStrERunnerBAliBASE balibaseProblemName dataDirectory NumberOfEvaluations PopulationSize
```
* balibaseProblemName: the BAliBASE instance name, for instance `BB12001`. 
* dataDirectory: The Path that contains the Structural Information files (PDB's (*.pdb) and Strike Contact Matrix (*.contacts)) of the sequences to align and the Pre-Computed alignments to use to generate the Initial population of the algorithm.  
* NumberOfEvaluations: Number of the Maximun Evaluations of the algorithm.
* PopulationSize: Size of the population of the algorithm

For solving BAliBASE problems, jMetalMSA searches the Sequences Files in FASTA format, the Contacts files and the pre-computed alignments, as follows:

* Directory with the PDB Files:   dataDirectory + /aligned/strike/ + Group + / + balibaseProblemName + /
* Balibase Directory: dataDirectory + /bb3_release/ + Group + /
* Directory with the PreAlignments:  dataDirectory + /aligned/ + Group + / + balibaseProblemName;

## Results 

The output of the program are two files:
* `VAR.tsv`: contains the Pareto front approximation. For each solution, this file contains a line with the values of the three objectives.
* `FUN.tsv`: contains the Pareto set approximation. Each solution is represented in FASTA format.
