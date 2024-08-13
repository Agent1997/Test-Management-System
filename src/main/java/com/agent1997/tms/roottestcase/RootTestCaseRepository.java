package com.agent1997.tms.roottestcase;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RootTestCaseRepository  extends JpaRepository<RootTestCaseEntity, String> {
}
