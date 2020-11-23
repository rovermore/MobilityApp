package com.example.mobilityapp

import com.example.mobilityapp.model.getCompanyZoneIdList
import junit.framework.TestCase
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TransportTest {

    private val transportList = TransportListMock.transportList
    private val companyZoneIdList = TransportListMock.companyZoneIdList

    @Before
    fun setup() {

    }

    @Test
    fun `check if getCompanyZoneIdList function works`() {
        val returnedCompanyZoneIdList = transportList.getCompanyZoneIdList()
        junit.framework.Assert.assertEquals(returnedCompanyZoneIdList.size, this@TransportTest.companyZoneIdList.size)
        junit.framework.Assert.assertEquals(returnedCompanyZoneIdList[0], this@TransportTest.companyZoneIdList[0])
        junit.framework.Assert.assertEquals(returnedCompanyZoneIdList[1], this@TransportTest.companyZoneIdList[1])

    }
}