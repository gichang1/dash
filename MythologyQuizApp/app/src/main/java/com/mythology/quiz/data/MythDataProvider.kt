package com.mythology.quiz.data

object MythDataProvider {

    fun getInitialCharacters(): List<MythCharacter> = listOf(
        // 올림포스 12신
        MythCharacter(
            nameKorean = "제우스",
            nameGreek = "Zeus",
            category = "올림포스 12신",
            description = "올림포스의 주신으로 신들의 왕. 하늘과 번개를 다스리며, 독수리와 번개가 상징이다. 크로노스와 레아의 아들로 티탄 전쟁에서 승리해 우주의 지배자가 됐다.",
            shortHint = "번개를 무기로 사용하는 신들의 왕",
            attributes = "번개, 독수리, 왕관, 올림포스",
            emoji = "⚡",
            characterDrawable = "char_zeus",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "헤라",
            nameGreek = "Hera",
            category = "올림포스 12신",
            description = "결혼과 가정의 여신이자 제우스의 아내. 공작과 소가 상징이며, 질투심이 강하기로 유명하다. 제우스의 수많은 연인들과 그 자녀들을 박해했다.",
            shortHint = "결혼의 여신이자 제우스의 아내",
            attributes = "공작, 결혼, 질투, 왕비",
            emoji = "👑",
            characterDrawable = "char_hera",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "포세이돈",
            nameGreek = "Poseidon",
            category = "올림포스 12신",
            description = "바다의 신으로 삼지창이 상징. 지진을 일으킬 수 있어 '땅을 흔드는 자'라 불렸다. 말을 창조했으며 아테네와 도시의 수호신 자리를 두고 경쟁했다.",
            shortHint = "삼지창을 들고 바다를 다스리는 신",
            attributes = "삼지창, 바다, 지진, 말",
            emoji = "🔱",
            characterDrawable = "char_poseidon",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "아테나",
            nameGreek = "Athena",
            category = "올림포스 12신",
            description = "지혜와 전략의 여신. 제우스의 머리에서 완전 무장한 채로 태어났다. 올빼미와 올리브나무가 상징이며, 아테네 도시의 수호신이다.",
            shortHint = "지혜의 여신, 제우스의 머리에서 탄생",
            attributes = "올빼미, 올리브나무, 지혜, 투구",
            emoji = "🦉",
            characterDrawable = "char_athena",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "아폴론",
            nameGreek = "Apollo",
            category = "올림포스 12신",
            description = "태양, 예언, 음악, 시의 신. 황금 활과 리라가 상징이다. 아르테미스의 쌍둥이 남매이며, 델포이의 신탁을 주관한다.",
            shortHint = "태양과 음악의 신, 아르테미스의 쌍둥이",
            attributes = "태양, 활, 리라, 예언, 월계관",
            emoji = "☀️",
            characterDrawable = "char_apollo",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "아르테미스",
            nameGreek = "Artemis",
            category = "올림포스 12신",
            description = "달과 사냥의 여신. 은빛 활과 달이 상징이며, 순결의 여신이기도 하다. 아폴론의 쌍둥이 누이로, 사냥꾼과 야생동물을 보호한다.",
            shortHint = "달과 사냥의 여신, 아폴론의 쌍둥이",
            attributes = "달, 은활, 사냥, 사슴",
            emoji = "🌙",
            characterDrawable = "char_artemis",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "아프로디테",
            nameGreek = "Aphrodite",
            category = "올림포스 12신",
            description = "사랑과 아름다움의 여신. 바다의 거품에서 태어났으며, 장미와 비둘기가 상징이다. 헤파이스토스의 아내이지만 아레스를 사랑했다.",
            shortHint = "사랑과 미의 여신, 바다 거품에서 탄생",
            attributes = "장미, 비둘기, 사랑, 아름다움",
            emoji = "🌹",
            characterDrawable = "char_aphrodite",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "아레스",
            nameGreek = "Ares",
            category = "올림포스 12신",
            description = "전쟁의 신으로 폭력적이고 잔인한 전투를 즐긴다. 창과 방패가 상징이며, 독수리와 개가 성스러운 동물이다. 아프로디테와 사랑에 빠졌다.",
            shortHint = "전쟁의 신, 폭력적인 전투를 즐김",
            attributes = "창, 방패, 전쟁, 피",
            emoji = "⚔️",
            characterDrawable = "char_ares",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "헤파이스토스",
            nameGreek = "Hephaestus",
            category = "올림포스 12신",
            description = "불과 대장장이의 신. 신들의 무기와 갑옷을 만드는 명장. 태어날 때 다리가 불편하여 올림포스에서 버려졌지만, 뛰어난 기술로 신들의 인정을 받았다.",
            shortHint = "불과 대장장이의 신, 신들의 무기를 제작",
            attributes = "불, 망치, 대장간, 갑옷",
            emoji = "🔨",
            characterDrawable = "char_hephaestus",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "헤르메스",
            nameGreek = "Hermes",
            category = "올림포스 12신",
            description = "신들의 전령이자 상업, 도둑, 여행자의 신. 날개 달린 샌들과 지팡이(케리케이온)가 상징이다. 죽은 자를 저승으로 안내하기도 한다.",
            shortHint = "날개 달린 샌들을 신은 신들의 전령",
            attributes = "날개 샌들, 지팡이, 전령, 도둑",
            emoji = "🪄",
            characterDrawable = "char_hermes",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "데메테르",
            nameGreek = "Demeter",
            category = "올림포스 12신",
            description = "농업과 수확의 여신. 밀 이삭과 낫이 상징이며, 딸 페르세포네가 하데스에게 납치된 후 슬픔으로 대지를 황폐하게 만들었다. 계절의 변화를 만들어낸 신.",
            shortHint = "농업의 여신, 페르세포네의 어머니",
            attributes = "밀, 낫, 농업, 수확",
            emoji = "🌾",
            characterDrawable = "char_demeter",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "디오니소스",
            nameGreek = "Dionysus",
            category = "올림포스 12신",
            description = "포도주와 축제의 신. 포도 넝쿨과 티르소스(지팡이)가 상징이다. 제우스와 인간 세멜레 사이에서 태어난 반신으로, 광기와 환희를 가져다준다.",
            shortHint = "포도주와 축제의 신",
            attributes = "포도, 포도주, 티르소스, 축제",
            emoji = "🍇",
            characterDrawable = "char_dionysus",
            difficulty = 2
        ),

        // 기타 신들
        MythCharacter(
            nameKorean = "하데스",
            nameGreek = "Hades",
            category = "기타 신",
            description = "저승의 신이자 죽은 자들의 왕. 제우스의 형이며, 머리에 쓰면 투명해지는 투구가 상징이다. 페르세포네를 납치하여 아내로 삼았다.",
            shortHint = "저승을 다스리는 죽음의 신",
            attributes = "저승, 죽음, 투명 투구, 케르베로스",
            emoji = "💀",
            characterDrawable = "char_hades",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "페르세포네",
            nameGreek = "Persephone",
            category = "기타 신",
            description = "데메테르의 딸이자 하데스의 아내. 하데스에게 납치되어 저승의 여왕이 됐다. 석류를 먹어 1년의 절반은 저승에서, 절반은 지상에서 지낸다.",
            shortHint = "저승의 여왕, 데메테르의 딸",
            attributes = "석류, 꽃, 저승, 봄",
            emoji = "🌺",
            characterDrawable = "char_persephone",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "에로스",
            nameGreek = "Eros",
            category = "기타 신",
            description = "사랑의 신이자 아프로디테의 아들. 황금 화살을 맞으면 사랑에 빠지고, 납 화살을 맞으면 사랑을 거부한다. 프시케와의 사랑 이야기로 유명하다.",
            shortHint = "사랑의 화살을 쏘는 날개 달린 신",
            attributes = "화살, 활, 날개, 사랑",
            emoji = "💘",
            characterDrawable = "char_eros",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "니케",
            nameGreek = "Nike",
            category = "기타 신",
            description = "승리의 여신. 날개가 있으며 월계관과 야자수 가지를 들고 있다. 제우스와 아테나의 전령으로, 전쟁에서의 승리를 가져다준다.",
            shortHint = "승리의 여신, 날개를 가진 존재",
            attributes = "날개, 월계관, 승리, 트로피",
            emoji = "🏆",
            characterDrawable = "char_nike",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "히프노스",
            nameGreek = "Hypnos",
            category = "기타 신",
            description = "잠의 신. 타나토스(죽음의 신)의 쌍둥이 형제. 양귀비꽃과 날개가 상징이며, 신들과 인간 모두를 잠재울 수 있는 능력을 가졌다.",
            shortHint = "잠을 가져다주는 신",
            attributes = "잠, 양귀비, 날개, 밤",
            emoji = "😴",
            characterDrawable = "char_hypnos",
            difficulty = 3
        ),

        // 영웅들
        MythCharacter(
            nameKorean = "헤라클레스",
            nameGreek = "Heracles",
            category = "영웅",
            description = "그리스 최고의 영웅. 제우스와 알크메네의 아들로, 12가지 과업을 완수한 것으로 유명하다. 사자 가죽을 걸치고 곤봉을 무기로 사용했다.",
            shortHint = "12가지 과업을 완수한 최강의 영웅",
            attributes = "사자 가죽, 곤봉, 괴력, 12과업",
            emoji = "💪",
            characterDrawable = "char_heracles",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "페르세우스",
            nameGreek = "Perseus",
            category = "영웅",
            description = "제우스와 다나에의 아들. 메두사의 머리를 베고 안드로메다를 구한 영웅. 날개 달린 신발과 투명 투구, 메두사의 머리가 담긴 가방을 가졌다.",
            shortHint = "메두사를 처치하고 안드로메다를 구한 영웅",
            attributes = "메두사, 날개 신발, 방패, 별자리",
            emoji = "🛡️",
            characterDrawable = "char_perseus",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "테세우스",
            nameGreek = "Theseus",
            category = "영웅",
            description = "아테네의 영웅으로 미노타우로스를 물리쳤다. 아리아드네의 실타래 덕분에 미로에서 탈출했다. 아테네의 건국 영웅으로 존경받는다.",
            shortHint = "미노타우로스를 처치한 아테네의 영웅",
            attributes = "미로, 실타래, 아테네, 검",
            emoji = "🗡️",
            characterDrawable = "char_theseus",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "오디세우스",
            nameGreek = "Odysseus",
            category = "영웅",
            description = "이타카의 왕. 트로이 전쟁에서 트로이 목마를 고안한 지략가. 전쟁 후 고향으로 돌아오는 10년의 여정이 '오디세이아'에 기록됐다.",
            shortHint = "트로이 목마를 고안한 지략의 영웅",
            attributes = "트로이 목마, 지략, 활, 바다",
            emoji = "🐴",
            characterDrawable = "char_odysseus",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "아킬레우스",
            nameGreek = "Achilles",
            category = "영웅",
            description = "트로이 전쟁 최고의 전사. 어머니 테티스가 스틱스 강에 담가 무적이 되었지만, 발꿈치만 약점이었다. 파리스의 화살에 발꿈치를 맞아 사망했다.",
            shortHint = "발꿈치가 유일한 약점인 트로이 전쟁의 영웅",
            attributes = "발꿈치, 갑옷, 창, 분노",
            emoji = "⚡",
            characterDrawable = "char_achilles",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "이아손",
            nameGreek = "Jason",
            category = "영웅",
            description = "아르고 원정대의 대장으로 황금 양털을 찾아 여정을 떠났다. 마법사 메데이아의 도움으로 과업을 완수했다. 아르고나우타이 이야기의 주인공.",
            shortHint = "황금 양털을 찾아 아르고 원정대를 이끈 영웅",
            attributes = "황금 양털, 아르고호, 메데이아",
            emoji = "⚓",
            characterDrawable = "char_jason",
            difficulty = 3
        ),
        MythCharacter(
            nameKorean = "오르페우스",
            nameGreek = "Orpheus",
            category = "영웅",
            description = "음악의 천재로, 리라 연주로 신들과 자연까지 감동시켰다. 아내 에우리디케를 저승에서 구하러 갔지만 뒤를 돌아보는 바람에 실패했다.",
            shortHint = "음악으로 저승까지 내려간 음악가",
            attributes = "리라, 음악, 저승, 사랑",
            emoji = "🎵",
            characterDrawable = "char_orpheus",
            difficulty = 3
        ),

        // 괴물과 신화적 존재
        MythCharacter(
            nameKorean = "메두사",
            nameGreek = "Medusa",
            category = "괴물",
            description = "뱀 머리카락을 가진 고르곤 세 자매 중 하나. 눈을 마주치면 돌로 변한다. 포세이돈의 애인이었으나 아테나의 저주로 괴물이 됐다. 페르세우스에게 처치됐다.",
            shortHint = "눈을 마주치면 돌이 되는 뱀 머리카락 괴물",
            attributes = "뱀 머리카락, 돌, 고르곤, 날개",
            emoji = "🐍",
            characterDrawable = "char_medusa",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "미노타우로스",
            nameGreek = "Minotaur",
            category = "괴물",
            description = "크레타 섬의 미로에 사는 반인반우(半人半牛) 괴물. 크레타 왕 미노스의 아내 파시파에가 낳았다. 매년 아테네에서 바치는 공물을 먹었다가 테세우스에게 처치됐다.",
            shortHint = "미로에 사는 반인반우 괴물",
            attributes = "황소 머리, 미로, 크레타, 공물",
            emoji = "🐂",
            characterDrawable = "char_minotaur",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "키클로페스",
            nameGreek = "Cyclops",
            category = "괴물",
            description = "이마 한가운데 눈이 하나 있는 거인족. 대장장이로 제우스의 번개를 만들었다. 오디세우스가 폴리페모스라는 키클로페스의 동굴에 갇혔다가 탈출한 이야기가 유명하다.",
            shortHint = "눈이 하나인 거인 대장장이",
            attributes = "외눈, 거인, 대장간, 동굴",
            emoji = "👁️",
            characterDrawable = "char_cyclops",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "케르베로스",
            nameGreek = "Cerberus",
            category = "괴물",
            description = "저승의 입구를 지키는 세 머리 달린 개. 하데스의 파수꾼으로, 산 자는 저승에 들어오지 못하고 죽은 자는 다시 나오지 못하게 막는다.",
            shortHint = "저승의 입구를 지키는 세 머리 개",
            attributes = "세 머리, 개, 저승, 파수꾼",
            emoji = "🐕",
            characterDrawable = "char_cerberus",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "히드라",
            nameGreek = "Hydra",
            category = "괴물",
            description = "레르나 늪에 사는 물뱀 괴물. 머리가 여러 개 달렸으며, 머리를 자르면 두 개가 자라난다. 헤라클레스의 12과업 중 하나로 처치됐다.",
            shortHint = "머리를 자르면 다시 자라는 다두 물뱀",
            attributes = "다두, 물뱀, 독, 레르나",
            emoji = "🐲",
            characterDrawable = "char_hydra",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "스핑크스",
            nameGreek = "Sphinx",
            category = "괴물",
            description = "사자의 몸통에 인간 여성의 머리를 가진 괴물. 테베 앞에서 수수께끼를 내어 맞추지 못하면 잡아먹었다. 오이디푸스가 수수께끼를 풀자 스스로 죽었다.",
            shortHint = "수수께끼를 내는 사자 몸통의 괴물",
            attributes = "수수께끼, 사자 몸, 여성 머리, 테베",
            emoji = "🦁",
            characterDrawable = "char_sphinx",
            difficulty = 3
        ),

        // 티탄
        MythCharacter(
            nameKorean = "프로메테우스",
            nameGreek = "Prometheus",
            category = "티탄",
            description = "티탄 신족으로 인류의 은인. 신들의 불을 훔쳐 인간에게 주었다. 이 죄로 코카서스 산에 묶여 독수리에게 매일 간을 쪼아 먹히는 형벌을 받았다.",
            shortHint = "인간에게 불을 가져다준 티탄",
            attributes = "불, 독수리, 형벌, 인류",
            emoji = "🔥",
            characterDrawable = "char_prometheus",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "크로노스",
            nameGreek = "Cronos",
            category = "티탄",
            description = "티탄의 왕이자 제우스의 아버지. 자식에게 왕위를 빼앗길 것이라는 예언을 들어 자신의 자녀들을 모두 삼켰다. 결국 제우스에게 타르타로스에 갇혔다.",
            shortHint = "자식을 삼킨 티탄의 왕",
            attributes = "낫, 시간, 티탄, 왕",
            emoji = "⏰",
            characterDrawable = "char_cronos",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "아틀라스",
            nameGreek = "Atlas",
            category = "티탄",
            description = "티탄 전쟁에서 패한 뒤 영원히 하늘을 떠받치는 형벌을 받은 티탄. 헤라클레스가 황금 사과를 가져오는 대가로 잠시 하늘을 대신 떠받쳤다.",
            shortHint = "영원히 하늘을 떠받치는 티탄",
            attributes = "하늘, 형벌, 티탄, 힘",
            emoji = "🌍",
            characterDrawable = "char_atlas",
            difficulty = 2
        ),

        // 기타 인물
        MythCharacter(
            nameKorean = "판도라",
            nameGreek = "Pandora",
            category = "인간",
            description = "신들이 만든 최초의 인간 여성. 절대 열어서는 안 되는 상자를 가지고 있었지만 호기심을 참지 못해 열어 온갖 재앙을 세상에 풀었다. 마지막으로 희망만 남았다.",
            shortHint = "호기심으로 상자를 열어 재앙을 풀어낸 여성",
            attributes = "상자, 호기심, 재앙, 희망",
            emoji = "📦",
            characterDrawable = "char_pandora",
            difficulty = 1
        ),
        MythCharacter(
            nameKorean = "이카로스",
            nameGreek = "Icarus",
            category = "인간",
            description = "대장장이 다이달로스의 아들. 밀랍으로 만든 날개로 탈출했지만, 아버지의 경고를 무시하고 태양에 너무 가까이 날다가 날개가 녹아 바다에 빠져 죽었다.",
            shortHint = "날개가 녹아 바다에 떨어진 소년",
            attributes = "날개, 밀랍, 태양, 추락",
            emoji = "🪶",
            characterDrawable = "char_icarus",
            difficulty = 2
        ),
        MythCharacter(
            nameKorean = "나르키소스",
            nameGreek = "Narcissus",
            category = "인간",
            description = "아름다운 청년으로, 물에 비친 자신의 모습에 빠져 결국 죽어 수선화가 됐다. 님프 에코가 사랑을 고백했으나 거절당해 메아리로 변했다.",
            shortHint = "자신의 모습에 반해 수선화가 된 미소년",
            attributes = "물, 수선화, 에코, 자기애",
            emoji = "🌼",
            characterDrawable = "char_narcissus",
            difficulty = 3
        ),
        MythCharacter(
            nameKorean = "미다스",
            nameGreek = "Midas",
            category = "인간",
            description = "손대는 것이 모두 황금으로 변하는 황금의 손을 소원으로 받은 왕. 음식도 딸도 황금이 되자 이를 후회하고 소원을 취소해 달라 빌었다.",
            shortHint = "황금 손을 가져 모든 것을 황금으로 만든 왕",
            attributes = "황금, 손, 욕심, 후회",
            emoji = "✨",
            characterDrawable = "char_midas",
            difficulty = 2
        )
    )

    fun getCategories(): List<String> = listOf(
        "전체",
        "올림포스 12신",
        "기타 신",
        "영웅",
        "괴물",
        "티탄",
        "인간"
    )
}
