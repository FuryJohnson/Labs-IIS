jQuery(document).ready(function ($) {
    console.log("DOM ready");

    var convertStringToIntArray = function (s) {
        var a = [];
        for (var i = 0; i < s.length; i++)
        a.push(s[i].charCodeAt(0) - "0".charCodeAt(0));
        return a;
    }

    // Fix sign issues with remainder operation (get "real" modulus operation)
    var mod = function(n, modulus) {
        if (n < 0)
            return  (modulus + (n % modulus)) % modulus;
        return n % modulus;
    };
    
    var rkSearch = function (text, pattern) {
        textA = convertStringToIntArray(text);
        patternA = convertStringToIntArray(pattern);
        console.log("Searching for " + pattern + " in " + text, patternA, textA);
        var n = textA.length;
        var m = patternA.length;
        var d = 10; // because we're just using 0-9
        var prime = 11;
        var h = Math.pow(d, m - 1);
        var p = 0;
        var t = 0;

        // Pre-processing the pattern (i.e. computing its hash)
        for (i = 0; i < m; i++) {
            p = mod(d*p + patternA[i], prime);
            t = mod(d*t + textA[i], prime);
            //console.log(p,t);
        }
        console.log("Computed p = " + p + ", t = " + t);

        var results = {
            text: textA,
            pattern: patternA,
            tValues: [],
            spuriousHits: [],
            matches: []
        };
        // Searching for matches
        for (i = 0; i < n - m; i++) {
            if (t === p) {
                if (text.substring(i, i + m) === pattern) {
                    console.log("Found match at i = " + i);
                    results.matches.push(i);
                } else {
                    console.log("Encountered spurious hit at i = " + i);
                    results.spuriousHits.push(i);
                }
            }

            console.log("i = " + i +", t = " + t); // + ", current substring = " + text.substring(i, i + m));
            console.log("Taking out " + textA[i] + ", adding in " + textA[i + m]);
            t = mod(d*(t - (h * textA[i])) + textA[i + m], prime);
            results.tValues.push(t);
        }
        return results;
    }

    var displayResults = function (results) {
        results = results || {};
        var output = "<ul>";
        $.each(results, function (key, value) {
            output += "<li>" + key + ": " + value + "</li>";
        });
        output += "</ul>";
        $("div#results").show().html(output);
    }

    $("button#search-button").on("click", function (e) {
        e.preventDefault();
        e.stopPropagation();
        console.log("Starting search");
        var pattern = $("#pattern").val();
        var text = $("#text").val();
        if (typeof text !== "string" || typeof pattern !== "string") {
            console.error("Received invalid input");
            return;
        }
        var results = rkSearch(text, pattern);
        displayResults(results);
    });
});