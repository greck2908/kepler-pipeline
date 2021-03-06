/* vprojg.f -- translated by f2c (version 19980913).
   You must link the resulting object file with the libraries:
	-lf2c -lm   (in that order)
*/

#include "f2c.h"

/* $Procedure      VPROJG ( Vector projection, general dimension ) */
/* Subroutine */ int vprojg_(doublereal *a, doublereal *b, integer *ndim, 
	doublereal *p)
{
    doublereal scale, adotb, bdotb;
    extern /* Subroutine */ int vsclg_(doublereal *, doublereal *, integer *, 
	    doublereal *);
    extern doublereal vdotg_(doublereal *, doublereal *, integer *);

/* $ Abstract */

/*      VPROJG finds the projection of the one vector onto another */
/*      vector.  All vectors are of arbitrary dimension. */

/* $ Disclaimer */

/*     THIS SOFTWARE AND ANY RELATED MATERIALS WERE CREATED BY THE */
/*     CALIFORNIA INSTITUTE OF TECHNOLOGY (CALTECH) UNDER A U.S. */
/*     GOVERNMENT CONTRACT WITH THE NATIONAL AERONAUTICS AND SPACE */
/*     ADMINISTRATION (NASA). THE SOFTWARE IS TECHNOLOGY AND SOFTWARE */
/*     PUBLICLY AVAILABLE UNDER U.S. EXPORT LAWS AND IS PROVIDED "AS-IS" */
/*     TO THE RECIPIENT WITHOUT WARRANTY OF ANY KIND, INCLUDING ANY */
/*     WARRANTIES OF PERFORMANCE OR MERCHANTABILITY OR FITNESS FOR A */
/*     PARTICULAR USE OR PURPOSE (AS SET FORTH IN UNITED STATES UCC */
/*     SECTIONS 2312-2313) OR FOR ANY PURPOSE WHATSOEVER, FOR THE */
/*     SOFTWARE AND RELATED MATERIALS, HOWEVER USED. */

/*     IN NO EVENT SHALL CALTECH, ITS JET PROPULSION LABORATORY, OR NASA */
/*     BE LIABLE FOR ANY DAMAGES AND/OR COSTS, INCLUDING, BUT NOT */
/*     LIMITED TO, INCIDENTAL OR CONSEQUENTIAL DAMAGES OF ANY KIND, */
/*     INCLUDING ECONOMIC DAMAGE OR INJURY TO PROPERTY AND LOST PROFITS, */
/*     REGARDLESS OF WHETHER CALTECH, JPL, OR NASA BE ADVISED, HAVE */
/*     REASON TO KNOW, OR, IN FACT, SHALL KNOW OF THE POSSIBILITY. */

/*     RECIPIENT BEARS ALL RISK RELATING TO QUALITY AND PERFORMANCE OF */
/*     THE SOFTWARE AND ANY RELATED MATERIALS, AND AGREES TO INDEMNIFY */
/*     CALTECH AND NASA FOR ALL THIRD-PARTY CLAIMS RESULTING FROM THE */
/*     ACTIONS OF RECIPIENT IN THE USE OF THE SOFTWARE. */

/* $ Required_Reading */

/*     None. */

/* $ Keywords */

/*      VECTOR */

/* $ Declarations */
/* $ Brief_I/O */

/*      VARIABLE  I/O  DESCRIPTION */
/*      --------  ---  -------------------------------------------------- */
/*       A         I    The vector to be projected. */
/*       B         I    The vector onto which A is to be projected. */
/*       NDIM      I    Dimension of A, B, and P. */
/*       P         O    The projection of A onto B. */

/* $ Detailed_Input */

/*      A     is a double precision vector of arbitrary dimension.  This */
/*            vector is to be projected onto the vector B. */

/*      B     is a double precision vector of arbitrary dimension.  This */
/*            vector is the vector which receives the projection. */

/*      NDIM  is the dimension of A, B and P. */

/* $ Detailed_Output */

/*      P     is a double precision vector of arbitrary dimension */
/*            containing the projection of A onto B.  P may overwrite */
/*            either A or B.  (P is necessarily parallel to B.) */

/* $ Parameters */

/*     None. */

/* $ Particulars */

/*      The projection of a vector A onto a vector B is, by definition, */
/*      that component of A which is parallel to B.  To find this */
/*      component it is enough to find the scalar ratio of the length */
/*      of B to the projection of A onto B, and then use this number to */
/*      scale the length of B.  This ratio is given by */

/*            RATIO = (A DOT B) / (B DOT B) */

/*      where DOT denotes the general vector dot product.  This routine */
/*      does not attempt to divide by zero in the event that B is the */
/*      zero vector. */

/* $ Examples */

/*      The following table gives sample inputs and results from calling */
/*      VPROJG. */

/*      A                  B                  NDIM            P */
/*      ----------------------------------------------------------------- */
/*      (6, 6, 6, 6)      ( 2, 0, 0, 0)        4            (6, 0, 0, 0) */
/*      (6, 6, 6, 0)      (-3, 0, 0, 0)        4            (6, 0, 0, 0) */
/*      (6, 6, 0, 0)      ( 0, 7, 0, 0)        4            (0, 6, 0, 0) */
/*      (6, 0, 0, 0)      ( 0, 0, 9, 0)        4            (0, 0, 0, 0) */

/* $ Restrictions */

/*      No error detection or recovery schemes are incorporated into this */
/*      subroutine except to insure that no attempt is made to divide by */
/*      zero.  Thus, the user is required to make sure that the vectors */
/*      A and B are such that no floating point overflow will occur when */
/*      the dot products are calculated. */

/* $ Exceptions */

/*      Error free. */

/* $ Files */

/*      None. */

/* $ Author_and_Institution */

/*      N.J. Bachman    (JPL) */
/*      H.A. Neilan     (JPL) */
/*      W.L. Taber      (JPL) */

/* $ Literature_References */

/*      REFERENCE: Any reasonable calculus text (for example Thomas) */

/* $ Version */

/* -     SPICELIB Version 1.0.2, 22-AUG-2001 (EDW) */

/*         Corrected ENDIF to END IF. */

/* -     SPICELIB Version 1.0.1, 10-MAR-1992 (WLT) */

/*         Comment section for permuted index source lines was added */
/*         following the header. */

/* -     SPICELIB Version 1.0.0, 31-JAN-1990 (WLT) */

/* -& */
/* $ Index_Entries */

/*     n-dimensional vector projection */

/* -& */
/* $ Revisions */

/* -     Beta Version 1.1.0, 17-FEB-1989 (HAN) (NJB) */

/*         Contents of the Exceptions section was changed */
/*         to "error free" to reflect the decision that the */
/*         module will never participate in error handling. */

/*         The declaration of the unused variable I was removed. */
/* -& */


    adotb = vdotg_(a, b, ndim);
    bdotb = vdotg_(b, b, ndim);

    if (bdotb == 0.) {
	scale = 0.;
    } else {
	scale = adotb / bdotb;
    }

    vsclg_(&scale, b, ndim, p);

    return 0;
} /* vprojg_ */

