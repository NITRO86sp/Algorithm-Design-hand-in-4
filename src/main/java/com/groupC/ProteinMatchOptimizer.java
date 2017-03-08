package com.groupC;

public class ProteinMatchOptimizer {

    public static void main(String[] args) {

        Aligner aligner = new Aligner(new CostsParser(args[0]).getCosts(), new ProteinParser(args[1]).createMap());
        aligner.align();

    }
}
