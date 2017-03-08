package com.groupC;

import java.util.Map;

public class Aligner {
    private Map<String, Integer> costs;
    private Integer[][] memos;
    private Map<String, String> proteins;
    private String first, second;
    private int gapCost = -4;

    public Aligner(Map<String, Integer> costs, Map<String, String> proteins) {
        this.costs = costs;
        this.proteins = proteins;
    }

    public void align() {
        String[] creatureNames = proteins.keySet().toArray(new String[proteins.size()]);
        for (int i = 0; i < proteins.size(); i++) {
            first = proteins.get(creatureNames[i]);
            for (int j = i + 1; j < proteins.size(); j++) {
                second = proteins.get(creatureNames[j]);
                memos = new Integer[first.length()][second.length()];
                System.out.println(creatureNames[i] + "--" + creatureNames[j] + "\n" + (OPT(first.length() - 1, second.length() - 1)));

                String results[] = getMatching();
                System.out.println(results[0]);
                System.out.println(results[1]);
            }
        }

    }

    private int OPT(int i, int j) {
        char a = first.charAt(i);
        char b = second.charAt(j);
        int alphaXiYj = costs.get(Character.toString(a) + Character.toString(b));
        Integer result = memos[i][j];
        if (result == null) {
            if (i == 0 || j == 0) {
                if (i == 0 && j == 0) result = alphaXiYj;
                else {
                    if (i == 0) {
                        result = Math.max((alphaXiYj + j * gapCost),
                                (Math.max(gapCost + OPT(i, j - 1), (j + 1) * gapCost)));
                    }
                    {
                        if (j == 0) {
                            result = Math.max((alphaXiYj + i * gapCost),
                                    (Math.max(gapCost + OPT(i - 1, j), (i + 1) * gapCost)));
                        }


                    }
                }
            } else
                result = Math.max(alphaXiYj + OPT(i - 1, j - 1),
                        Math.max((gapCost + OPT(i - 1, j)),
                                (gapCost + OPT(i, j - 1))));

        }
        memos[i][j] = result;
        return result;
    }

    private String[] getMatching() {
        String[] results = new String[2];
        StringBuilder topLine = new StringBuilder();
        StringBuilder bottomLine = new StringBuilder();

        int p = first.length() - 1, q = second.length() - 1;


        while (p != 0 && q != 0) {

            String x = Character.toString(first.charAt(p));
            String y = Character.toString(second.charAt(q));
            if (memos[p][q] - memos[p - 1][q - 1] == costs.get(x + y)) {
                topLine.append(x);
                bottomLine.append(y);
                p--;
                q--;
            } else if (memos[p][q] - memos[p][q - 1] == -4) {

                topLine.append("*");
                bottomLine.append(y);
                q--;

            } else if
                    (memos[p][q] - memos[p - 1][q] == -4) {
                topLine.append(x);
                bottomLine.append("*");
                p--;
            }

        }

        if (q != 0) {
            while (q >= 0) {
                String x = Character.toString(first.charAt(p));
                String y = Character.toString(second.charAt(q));
                if (memos[p][q] - (q * gapCost) == costs.get(x + y)) {
                    StringBuilder pad = new StringBuilder();
                    topLine.append(x);
                    bottomLine.append(y);
                    bottomLine.append(new StringBuilder(second.substring(0, q)).reverse());
                    for (int a = 0; a < q; a++) {
                        pad.append("*");
                    }
                    topLine.append(pad);
                    break;
                } else {
                    topLine.append("*");
                    bottomLine.append(y);
                    q--;
                }
            }

        } else if (p != 0) {
            while (p >= 0) {
                String x = Character.toString(first.charAt(p));
                String y = Character.toString(second.charAt(q));
                if (memos[p][q] - (p * gapCost) == costs.get(x + y)) {
                    StringBuilder pad = new StringBuilder();
                    topLine.append(x);
                    bottomLine.append(y);
                    topLine.append(new StringBuilder(first.substring(0, p)).reverse());
                    for (int a = 0; a < p; a++) {
                        pad.append("*");}
                        bottomLine.append(pad);

                    break;
                }else {
                    topLine.append(x);
                    bottomLine.append("*");
                    p--;
                }
            }


        } else {
            if (p == 0 && q == 0) {
                String x = Character.toString(first.charAt(p));
                String y = Character.toString(second.charAt(q));
                topLine.append(x);
                bottomLine.append(y);
            }
        }
        {
            results[0] = topLine.reverse().toString();
            results[1] = bottomLine.reverse().toString();

            return results;
        }

    }
}