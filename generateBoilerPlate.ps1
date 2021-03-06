$sourceFile = "src\\generated\\Solution.java"
echo "/*GENERATED FILE DO NOT EDIT BEFORE MAIN METHOD*/" > $sourceFile
$imports=@()
$fileContents=@()


function Write-SolutionFile
{
    param($message)
    echo $message >> $sourceFile
}

function Write-EmptyLine
{
    Write-SolutionFile " "
}

Get-ChildItem "src\graph" -Filter *.java | `
Foreach-Object{
    $content = (Get-Content $_.FullName ) -replace '/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+/' ,''
    $imports += $content| Select-String -pattern '^[ \t]*import .*;'
    $filteredContent = $content| `
         Where {$_ -notmatch '^[ \t]*//'`
           -and $_ -notmatch '^[ \t]*package.*;' `
           -and $_ -notmatch '^[ \t]*import .*;' `
           -and $_ -notmatch '^[ \t]*$'`
           -and $_ -notmatch '^[ \t]*\/[\*]*' `
           -and $_ -notmatch '^[ \t]*\*\/'`
           -and $_ -notmatch '^[ \t]*\*'}
           $fileContents += $filteredContent -replace '\\0' , ''
}

Write-SolutionFile "package generated;"
Write-SolutionFile $imports
Write-SolutionFile  " public class Solution{"
Write-SolutionFile '    //<editor-fold desc="Generated Code For Graph Infra using github.com/rkalhans/Graph Do not edit inside this fold" defaultstate="collapsed">'
Write-SolutionFile $fileContents
Write-SolutionFile "    //</editor-fold>"
Write-SolutionFile "    public static void main(String[] args){"
Write-SolutionFile "        // write your code here"
Write-SolutionFile "    }"
Write-SolutionFile "}"
