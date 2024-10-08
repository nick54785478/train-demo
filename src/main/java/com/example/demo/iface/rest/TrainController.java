package com.example.demo.iface.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.train.command.CreateTrainCommand;
import com.example.demo.domain.train.command.QueryTrainCommand;
import com.example.demo.iface.dto.CreateTrainResource;
import com.example.demo.iface.dto.TrainCreatedResource;
import com.example.demo.iface.dto.TrainDetailQueriedResource;
import com.example.demo.iface.dto.TrainQueriedResource;
import com.example.demo.service.TrainCommandService;
import com.example.demo.service.TrainQueryService;
import com.example.demo.util.BaseDataTransformer;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@Validated
@RequestMapping("/api/v1/train")
@RestController
@AllArgsConstructor
public class TrainController {

	TrainQueryService trainQueryService;
	TrainCommandService trainCommandService;

	/**
	 * 新增火車車次
	 * 
	 * @param resource
	 * @return 成功訊息
	 */
	@PostMapping("")
	public ResponseEntity<TrainCreatedResource> create(@RequestBody CreateTrainResource resource) {
		// DTO 轉換
		CreateTrainCommand command = BaseDataTransformer.transformData(resource, CreateTrainCommand.class);
		return new ResponseEntity<TrainCreatedResource>(
				BaseDataTransformer.transformData(trainCommandService.createTrain(command), TrainCreatedResource.class),
				HttpStatus.OK);
	}

	/**
	 * 取得該火車車次的資訊
	 * 
	 * @param trainNo
	 * @return 該火車車次的停靠站資訊
	 */
	@Validated
	@GetMapping("/{trainNo}/stops")
	public ResponseEntity<TrainQueriedResource> query(
			@Validated @PathVariable @Min(value = 1, message = "車次必須為正整數") Integer trainNo) {
		return new ResponseEntity<>(BaseDataTransformer.transformData(trainQueryService.queryTrainData(trainNo),
				TrainQueriedResource.class), HttpStatus.OK);
	}

	/**
	 * 查詢符合條件的火車資訊
	 * 
	 * @param trainNo  車次
	 * @param fromStop 起站
	 * @param toStop   迄站
	 * @param takeDate 出發日期
	 * @param time     出發時間
	 * @return 該火車車次的停靠站資訊
	 */
	@GetMapping("")
	public ResponseEntity<List<TrainDetailQueriedResource>> getTrainListBetweenStopSection(
			@RequestParam(required = false) Integer trainNo, @RequestParam String fromStop, @RequestParam String toStop,
			@RequestParam String takeDate, @RequestParam String time) {
		QueryTrainCommand command = new QueryTrainCommand(trainNo, fromStop, toStop, takeDate, time);
		return new ResponseEntity<>(BaseDataTransformer.transformData(
				trainQueryService.queryTrainDataByCondition(command), TrainDetailQueriedResource.class), HttpStatus.OK);
	}

}
