/*
 * Copyright 2017 United States Government as represented by the
 * Administrator of the National Aeronautics and Space Administration.
 * All Rights Reserved.
 * 
 * This file is available under the terms of the NASA Open Source Agreement
 * (NOSA). You should have received a copy of this agreement with the
 * Kepler source code; see the file NASA-OPEN-SOURCE-AGREEMENT.doc.
 * 
 * No Warranty: THE SUBJECT SOFTWARE IS PROVIDED "AS IS" WITHOUT ANY
 * WARRANTY OF ANY KIND, EITHER EXPRESSED, IMPLIED, OR STATUTORY,
 * INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY THAT THE SUBJECT SOFTWARE
 * WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR FREEDOM FROM
 * INFRINGEMENT, ANY WARRANTY THAT THE SUBJECT SOFTWARE WILL BE ERROR
 * FREE, OR ANY WARRANTY THAT DOCUMENTATION, IF PROVIDED, WILL CONFORM
 * TO THE SUBJECT SOFTWARE. THIS AGREEMENT DOES NOT, IN ANY MANNER,
 * CONSTITUTE AN ENDORSEMENT BY GOVERNMENT AGENCY OR ANY PRIOR RECIPIENT
 * OF ANY RESULTS, RESULTING DESIGNS, HARDWARE, SOFTWARE PRODUCTS OR ANY
 * OTHER APPLICATIONS RESULTING FROM USE OF THE SUBJECT SOFTWARE.
 * FURTHER, GOVERNMENT AGENCY DISCLAIMS ALL WARRANTIES AND LIABILITIES
 * REGARDING THIRD-PARTY SOFTWARE, IF PRESENT IN THE ORIGINAL SOFTWARE,
 * AND DISTRIBUTES IT "AS IS."
 * 
 * Waiver and Indemnity: RECIPIENT AGREES TO WAIVE ANY AND ALL CLAIMS
 * AGAINST THE UNITED STATES GOVERNMENT, ITS CONTRACTORS AND
 * SUBCONTRACTORS, AS WELL AS ANY PRIOR RECIPIENT. IF RECIPIENT'S USE OF
 * THE SUBJECT SOFTWARE RESULTS IN ANY LIABILITIES, DEMANDS, DAMAGES,
 * EXPENSES OR LOSSES ARISING FROM SUCH USE, INCLUDING ANY DAMAGES FROM
 * PRODUCTS BASED ON, OR RESULTING FROM, RECIPIENT'S USE OF THE SUBJECT
 * SOFTWARE, RECIPIENT SHALL INDEMNIFY AND HOLD HARMLESS THE UNITED
 * STATES GOVERNMENT, ITS CONTRACTORS AND SUBCONTRACTORS, AS WELL AS ANY
 * PRIOR RECIPIENT, TO THE EXTENT PERMITTED BY LAW. RECIPIENT'S SOLE
 * REMEDY FOR ANY SUCH MATTER SHALL BE THE IMMEDIATE, UNILATERAL
 * TERMINATION OF THIS AGREEMENT.
 */

package gov.nasa.kepler.ar.exporter.cbv;

import static gov.nasa.kepler.common.FitsConstants.*;
import static gov.nasa.spiffy.common.lang.StringUtils.truncate;
import gov.nasa.kepler.ar.exporter.FitsFileCreationTimeFormat;
import gov.nasa.kepler.common.FitsUtils;
import nom.tam.fits.Header;
import nom.tam.fits.HeaderCardException;

/**
 * 
 * Cotrending Basis Vector FITS primary header formatter.
 * @author Sean McCauliff
 *
 */
class CbvPrimaryHeaderFormatter {

    /**
     * 4.0 - K2 file name changes.
     * 3.0 - The first Java implementation revision.
     * 2.0 - skipped
     * 1.0 - Initial revision.  This was generated by a python script.
     */
    private static final String FILE_VERSION = "4.0";

    /**  Old file:
     * EXTEND  =                    T
EXTNAME = 'PRIMARY '           / name of extension
EXTVER  =                  1.0 / extension version number
FILEVER =                  1.0 / file version number
ORIGIN  = 'NASA/Ames'          / organization that generated this file
TELESCOP= 'Kepler  '           / telescope
DATE    = '2013-04-17'         / file creation date
CREATOR = '/Develop/designFITS/code/pdc-cbv/make_pdcfits.py r50267' / FITS SW
BVVER   = ' 9.0.2@51113'       / basis vector software revision
ALGORTHM= '  Remove poly detrend variables, SVD' / algorithm to generate vectors
DATAVER = '/lc/mpe_true/pdc-matlab-7831-474896/st-0' / data dir.
QUARTER =                   15 / quarter pertaining to this file
SEASON  =                    1 / mission season
DATA_REL=                   20 / version of data release notes
CHECKSUM= '8k688i678i678i67'   / HDU checksum updated 2013-04-17T15:00:25
DATASUM = '0       '           / data unit checksum updated 2013-04-17T15:00:25

     * @param source
     * @return
     * @throws HeaderCardException 
     */
    Header formatHeader(CbvPrimaryHeaderSource source, String checksumValue) throws HeaderCardException {
        Header h = new Header();
        
        h.addValue(SIMPLE_KW, SIMPLE_VALUE, SIMPLE_COMMENT);
        h.addValue(BITPIX_KW, 8, BITPIX_COMMENT);
        h.addValue(NAXIS_KW, 0, NAXIS_COMMENT);
        h.addValue(EXTEND_KW, EXTEND_VALUE, EXTEND_COMMENT);
        h.addValue(NEXTEND_KW, 2, NEXTEND_COMMENT);
        h.addValue(EXTNAME_KW, "PRIMARY", EXTNAME_COMMENT);
        h.addValue(EXTVER_KW, 1, EXTVER_COMMENT);
        h.addValue(ORIGIN_KW, ORIGIN_VALUE, ORIGIN_COMMENT);
        FitsFileCreationTimeFormat creationTime = new FitsFileCreationTimeFormat();
        String creationTimeStr = creationTime.format(source.generatedAt());
        h.addValue(DATE_KW, creationTimeStr, DATE_COMMENT);
        String creatorStr = source.pipelineTaskId() + " " + source.programName();
        h.addValue(CREATOR_KW, truncate(creatorStr, CREATOR_LENGTH), CREATOR_COMMENT);
        String procVerStr = source.subversionUrl() + " r" + source.subversionRevision();
        h.addValue(PROCVER_KW, truncate(procVerStr,PROCVER_LENGTH), PROCVER_COMMENT);
        h.addValue(FILEVER_KW, FILE_VERSION, FILEVER_COMMENT);
        h.addValue(TIMVERSN_KW, TIMVERSN_VALUE, TIMVERSN_COMMENT);
        h.addValue(TELESCOP_KW, TELESCOP_VALUE, TELESCOP_COMMENT);
        h.addValue(INSTRUME_KW, INSTRUME_VALUE, INSTRUME_COMMENT);
        if (source.isK2()) {
            h.addValue(CAMPAIGN_KW, source.k2Campaign(), CAMPAIGN_COMMENT);
        } else {
            h.addValue(QUARTER_KW, source.quarter(), QUARTER_COMMENT);
            h.addValue(SEASON_KW, source.season(), SEASON_COMMENT);
        }
        h.addValue(DATA_REL_KW, source.dataReleaseNumber(), DATA_REL_COMMENT);
        h.addValue(OBSMODE_KW, source.observingMode().toFitsKeywordValue(), OBSMODE_COMMENT);
        FitsUtils.addChecksum(h, checksumValue, source.generatedAt());
        return h;
    }
}
