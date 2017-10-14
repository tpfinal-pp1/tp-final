package com.TpFinal.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {



    private Utils(){

    }

    public static int cantLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }


   public static ArrayList<Object> Search(List<Object>Objects,String keyword) {

        ArrayList<Object> ret = new ArrayList<Object>();
       keyword=keyword.toUpperCase();
       System.out.println(keyword);
       for (Object op : Objects)
            {
                if(op.toString()!=null){
                    if(Utils.isPercentageMatch(op.toString(),keyword,50)){
                        ret.add(op);
                    }
                }

            }



        return ret;


    }


    public static boolean isPercentageMatch(String candidate,String keyword,int threshold){
        String trimmedCandidate=trimCandidate(candidate,keyword,4);

        if(trimmedCandidate==""){
            return false;
        }

        int percentage=percentageOfTextMatch(keyword,trimmedCandidate);

        if(percentage>threshold){

            return true;
        }
        return false;
    }
    public static String trimCandidate(String candidate,String keyword,int threshold){

        if(keyword.length()<candidate.length()) {
            int matchCount=0;
            int start=0;
            int end=0;
            int keywordIndex=0;
            boolean enoughMatch=false;


            candidate=candidate.toUpperCase();
            for (int i = 0; i < candidate.length(); i++) {



                boolean isMatch=candidate.charAt(i)==keyword.charAt(keywordIndex);
                // System.out.print(candidate.charAt(i)+" | "+keyword.charAt(keywordIndex));

                if(isMatch){ //if both chars match
                    if(start==0)start=i;
                    // System.out.println("   /");
                    matchCount++;
                    keywordIndex++;
                    if(matchCount>threshold){ //flaged as enough match
                        enoughMatch=true;
                        end=i;
                    }


                }

                if(!isMatch&&matchCount<=threshold){
                    //    System.out.println("   X");
                    matchCount=0;
                    start=0;
                    end=0;
                    keywordIndex=0;
                }
                if(!isMatch&&matchCount>threshold){

                    return candidate.substring(start,end);
                }

                if(keywordIndex>keyword.length()-1){
                    if (keyword.length()==1){
                        return candidate.substring(start,i);
                    }
                    else{
                        return candidate.substring(start-1,i);
                    }
                }


            }
            return "";

        }
        return "";
    }






    public static int percentageOfTextMatch(String s0, String s1)
    {                       // Trim and remove duplicate spaces
        int percentage = 0;
        s0 = s0.trim().replaceAll("\\s+", " ");
        s1 = s1.trim().replaceAll("\\s+", " ");
        percentage=(int) (100 - (float) LevenshteinDistance(s0, s1) * 100 / (float) (s0.length() + s1.length()));
        return percentage;
    }

    public static int LevenshteinDistance(String s0, String s1) {

        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;
        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++)
            cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {

            // initial cost of skipping prefix in String s1
            newcost[0] = j - 1;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {

                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete),
                        cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }













}