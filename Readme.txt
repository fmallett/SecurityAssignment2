DES Implementation by Fiona Mallett (c3289339) and Nathan Davidson (c3187164)

The main application begins and runs through the class "Application.java" accepting input and output text files as parameters.

Application.java
-Reads input 
-Chooses either to encrypt or decrypt based on first line (0 for encrypt, 1 for decrypt)
-Takes name of output file or creates a default one to store the results

Avalanche.java
-calculates the average avalanche effect at each of the 16 rounds
-for each version of DES (DES0, DES1, DES2, DES3) compared with the original plaintext and key

AvalanchePermutations.java
-Permutes the input (either plaintext or key) by one bit for the length of the input
-Creates an arraylist of each of the permutations

Decryption.java
-Takes cipher text and a key and decrypts it to produce plaintext using DES0

Encryption.java
-Takes plaintext and a key and encrypts it using whatever version of DES is provided

ExpansionPermutation.java
-Defines the ExpansionPermutation to be used in DES round function
-Takes a 32 bit input and produces a 48 bit output

FinalPermutation.java
-Performs the final permutation for DES
-Takes a 64 bit input and produces a 64 bit output 

Input.java
-Reads an input file using input stream
-Stores the data in a string for outside access

InverseETable.java
-Used during DES2 and DES3
-Permutes 32 bit input to a 32 bit output using the inverse_E_table provided in the spec

KeyGeneration.java
-Produces keys used in each round of DES
-Performs left shifts
-Uses PC1 and PC2

Output.java
-Formats data for display
-Prints output to a text file using the name provided by the user

PC1.java
-Used to generate key
-Takes 64 bit key to produce a 56 bit key

PC2.java
-Final permutation to produce valid keys
-56 bit input produces 48 bit output

Permutation.java
-Used at end of round function
-permutes 32bit input to 32 bit output

SBox.java
-Contains all s-boxes used in DES
-Contains the algorithm to read in row and column data (6 bits)and produces binary output (4 bits)


