#!/bin/sh

BASEDIR=`dirname $0`/..
SRCDIR=${BASEDIR}/src
OUTDIR=${BASEDIR}/bin
PRODUCTDIR=${BASEDIR}/product
MAINCLASS=csvcradle.Main

if [ -e ${OUTDIR} ]; then
  rm -rf ${OUTDIR}
fi

mkdir ${OUTDIR}
mkdir -p ${PRODUCTDIR}

javac -d ${OUTDIR} \
      -source 1.7 \
      -target 1.7 \
      -encoding UTF-8 \
      `find ${SRCDIR} -name "*.java"`

echo "Manifest-Version: 1.0" > manifest.mf
echo "Main-Class: ${MAINCLASS}" >> manifest.mf

jar cfm ${PRODUCTDIR}/csv-cradle.jar manifest.mf -C ${OUTDIR}/ .

rm manifest.mf

echo "#!/bin/sh" > ${PRODUCTDIR}/csv-cradle
echo 'java -jar `dirname $0`/csv-cradle.jar $@' >> ${PRODUCTDIR}/csv-cradle
chmod 755 ${PRODUCTDIR}/csv-cradle
