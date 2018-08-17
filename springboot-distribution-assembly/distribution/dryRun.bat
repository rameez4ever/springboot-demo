@echo off
for %%f in (.\*.jar) do (
	echo "Warming up repository for %%f. Please wait..."
 	java -jar %%f --thin.dryrun --thin.root=.
)
@echo "Done!!!"