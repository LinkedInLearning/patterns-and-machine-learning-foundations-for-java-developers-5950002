#!/bin/sh
DS=dataset.txt			# dataset
LABELS=labels.txt		# dog breeds, 1 per line
MAXPERDOG=${MAXPERDOG:-2000}	# max num of dog images per breed
MAXBREEDS=${MAXBREEDS:-2}	# max num of dog breeds (small number = faster training)

touch $DS $LABELS
rm $DS $LABELS

numdog=""		# contains actual # of dog images per breed

let mb=0
for i in [0-9][0-9][0-9].*
do
	label=`echo $i | sed 's/...\.\(.*\)/\1/'`
	let "num=0"
	for j in `ls $i`
	do
		test ${num} -ge $MAXPERDOG && break
		echo "$i/$j $label"  >> $DS
		let "num++"
	done
	numdog="${numdog} $num"
	echo $label >> $LABELS	
	let mb++
	test ${mb} -ge $MAXBREEDS && break
done 

echo         Dataset: $DS
echo          Labels: $LABELS
echo   Num of Breeds: `wc -l $LABELS | tr -s ' ' | cut -d' ' -s -f2`
echo   Num per Breed: "$MAXPERDOG : [$numdog]"
echo   Num of images: `wc -l $DS | tr -s ' ' | cut -d' ' -s -f2`
echo Size of dataset: `du -hs $DS | cut -f1`
